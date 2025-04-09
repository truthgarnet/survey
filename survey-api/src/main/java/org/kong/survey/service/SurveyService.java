package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyAnswerMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.QuestionRepository;
import org.kong.survey.repository.SurveyAnswerRepository;
import org.kong.survey.repository.SurveyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

    private final SurveyRepository surveyRepository;

    private final QuestionRepository questionRepository;

    private final SurveyAnswerRepository surveyAnswerRepository;

    private final SurveyMapper surveyMapper;

    private final QuestionMapper questionMapper;

    private final SurveyAnswerMapper surveyAnswerMapper;

    public PageDto<SurveyFindAll.Response> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> surveyList = surveyRepository.findAll(pageRequest);

        PageDto surveyFindAlls = surveyMapper.toSurveyFindAll(surveyList);

        PageDto<SurveyFindAll.Response> surveyPages = PageDto.<SurveyFindAll.Response>builder()
                        .content(surveyFindAlls.getContent())
                        .totalPages(surveyList.getTotalPages())
                        .totalElements(surveyList.getTotalElements())
                        .build();

        return surveyPages;
    }

    public Survey.Response findBySurveyId(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        List<QuestionEntity> questionEntities = questionRepository.findBySurvey_surveyId(surveyId);
        List<Question.Response> questionResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(questionEntities)) {
            questionResponses = questionMapper.toQuestionResponseList(questionEntities);
        }

        return surveyMapper.toSurveyResponse(survey, questionResponses);
    }

    public Survey.Response add(Survey.Request request) {
        // 1. Survey Add
        SurveyEntity survey = surveyMapper.toSurveyEntity(request);

        if (survey == null) {
            throw new RuntimeException("설문지를 찾을 수 없습니다.");
        }

        survey = surveyRepository.save(survey);

        // 2. Question Add
        List<QuestionEntity> questionEntities = questionMapper.toQuestionEntityList(survey, request.getQuestions());

        questionRepository.saveAll(questionEntities);

        // 3. Entity -> DTO
        List<Question.Response> questionResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(questionEntities)) {
            questionResponses = questionMapper.toQuestionResponseList(questionEntities);
        }
        Survey.Response surveyResponse = surveyMapper.toSurveyResponse(survey, questionResponses);

        return surveyResponse;
    }

    public Survey.Response updateAll(Integer surveyId, Survey.Request request) {
        // 1. Survey 가져오기
        surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        SurveyEntity changeSurvey = surveyMapper.toSurveyEntityUpdate(surveyId, request);
        surveyRepository.save(changeSurvey);

        Survey.Response surveyResponse;
        List<Question.Response> responses = new ArrayList<>();
        // 2. QuestionEntity 업데이트
        if (request.getQuestions() != null) {
            List<Question.Request> questions = request.getQuestions();
            List<QuestionEntity> changeQuestionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);

            questionRepository.saveAll(changeQuestionEntities);

            // 3. Entity -> DTO
            responses = questionMapper.toQuestionResponseList(changeQuestionEntities);
        }
        System.out.println("======responses: " + responses);
        surveyResponse = surveyMapper.toSurveyResponse(changeSurvey, responses);

        return surveyResponse;
    }

    public Survey.Response updatePart(int surveyId, Survey.Request request) {
        
        // 1. SurveyEntity 업데이트
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        // 1-1. Null 체크
        survey.updateCheckNull(survey.getSurveyTitle(), survey.getSurveyVersion(), survey.getUsedYn());

        SurveyEntity changeSurvey = surveyMapper.toSurveyEntityUpdate(surveyId, request);

        // 2. QuestionEntity 업데이트
        List<Question.Request> questions = request.getQuestions();
        List<QuestionEntity> questionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);

        questionRepository.saveAll(questionEntities);

        List<Question.Response> questionResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(questionEntities)) {
            questionResponses = questionMapper.toQuestionResponseList(questionEntities);
        }

        surveyRepository.save(changeSurvey);
        return surveyMapper.toSurveyResponse(changeSurvey, questionResponses);
    }

    public Integer delete(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        surveyRepository.delete(survey);
        return 1;
    }
}
