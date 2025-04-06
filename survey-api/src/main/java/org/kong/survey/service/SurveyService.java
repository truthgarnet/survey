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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SurveyAnswerMapper surveyAnswerMapper;

    public PageDto<SurveyFindAll.Response> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> surveyList = surveyRepository.findAll(pageRequest);

        PageDto surveyFindAlls = surveyMapper.toSurveyFindAll(surveyList);

        PageDto<SurveyFindAll.Response> surveyPages = new PageDto<>();
        surveyPages.setContent(surveyFindAlls.getContent());
        surveyPages.setTotalPages(surveyList.getTotalPages());
        surveyPages.setTotalElements(surveyList.getTotalElements());
        return surveyPages;
    }

    public Survey.Response findBySurveyId(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        List<QuestionEntity> questionEntities = questionRepository.findBySurvey_surveyId(surveyId);
        List<Question.Response> questionResponses = new ArrayList<>();
        if (questionEntities != null || questionEntities.size() > 0) {
            for (int i = 0; i < questionEntities.size(); i++) {

            }
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

        for (QuestionEntity question : questionEntities) {
            questionRepository.save(question);
        }

        // 3. Entity -> DTO
        List<Question.Response> questionResponses = new ArrayList<>();
        if (questionEntities != null || questionEntities.size() > 0) {
            questionResponses = questionMapper.toQuestionResponseList(questionEntities);
        }
        Survey.Response surveyResponse = surveyMapper.toSurveyResponse(survey, questionResponses);

        surveyResponse.setQuestions(questionResponses);

        return surveyResponse;
    }

    public Survey.Response updateAll(Integer surveyId, Survey.Request request) {
        // 1. Survey 가져오기
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        request.setSurveyId(surveyId);
        SurveyEntity changeSurvey = surveyMapper.toSurveyEntity(request);

        surveyRepository.save(changeSurvey);

        Survey.Response surveyResponse = new Survey.Response();
        List<Question.Response> responses = new ArrayList<>();
        // 2. QuestionEntity 업데이트
        if (request.getQuestions() != null) {
            log.info("Questions 입장: {}", request.getQuestions());
            List<Question.Request> questions = request.getQuestions();
            List<QuestionEntity> changeQuestionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);
            for (QuestionEntity question : changeQuestionEntities) {
                questionRepository.save(question);
            }

            // 3. Entity -> DTO
            responses = questionMapper.toQuestionResponseList(changeQuestionEntities);
            List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(changeQuestionEntities);

            surveyResponse.setQuestions(questionResponses);
        }

        surveyResponse = surveyMapper.toSurveyResponse(changeSurvey, responses);

        return surveyResponse;
    }

    public Survey.Response updatePart(int surveyId, Survey.Request request) {
        
        // 1. SurveyEntity 업데이트
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        if (survey.getSurveyTitle() != null) {
            survey.setSurveyTitle(request.getSurveyTitle());
        }

        // 1-1. 빈칸 허용
        if (survey.getSurveyVersion() != null) {
            survey.setSurveyVersion(request.getSurveyVersion());
        }

        if (survey.getUsedYn() != null) {
            survey.setUsedYn(request.isUsedYn());
        }

        request.setSurveyId(surveyId);
        SurveyEntity changeSurvey = surveyMapper.toSurveyEntity(request);

        // 2. QuestionEntity 업데이트
        List<Question.Request> questions = request.getQuestions();
        List<QuestionEntity> questionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);
        for (QuestionEntity question : questionEntities) {
            questionRepository.save(question);
        }

        List<Question.Response> questionResponses = new ArrayList<>();
        if (questionEntities != null || questionEntities.size() > 0) {
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
