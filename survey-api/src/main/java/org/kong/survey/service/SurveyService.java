package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.QuestionRepository;
import org.kong.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<SurveyFindAll.Response> findAll() {
        List<SurveyEntity> surveyList = surveyRepository.findAll();
        List<SurveyFindAll.Response> surveyFindAlls = surveyMapper.toSurveyFindAll(surveyList);

        return surveyFindAlls;
    }

    public Survey.Response findBySurveyId(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        return surveyMapper.toSurveyResponse(survey);
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
        Survey.Response surveyResponse = surveyMapper.toSurveyResponse(survey);
        List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(questionEntities);

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
        // 2. QuestionEntity 업데이트
        if (request.getQuestions() != null) {
            List<Question.Request> questions = request.getQuestions();
            List<QuestionEntity> changeQuestionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);
            for (QuestionEntity question : changeQuestionEntities) {
                questionRepository.save(question);
            }

            // 3. Entity -> DTO
            surveyResponse = surveyMapper.toSurveyResponse(changeSurvey);
            List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(changeQuestionEntities);

            surveyResponse.setQuestions(questionResponses);
        }



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

        surveyRepository.save(changeSurvey);
        return surveyMapper.toSurveyResponse(changeSurvey);
    }

    public Integer delete(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        surveyRepository.delete(survey);
        return 1;
    }
}
