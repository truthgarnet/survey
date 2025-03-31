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
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

        return surveyMapper.toSurveyResponse(survey);
    }

    public Survey.Response add(Survey.Request request) {
        SurveyEntity survey = surveyMapper.toSurveyEntity(request);

        if (survey == null) {
            throw new RuntimeException();
        }

        survey = surveyRepository.save(survey);

        return surveyMapper.toSurveyResponse(survey);
    }

    public Survey.Response updateAll(Integer surveyId, Survey.Request request) {
        surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

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

    public Survey.Response updatePart(int surveyId, Survey.Request request) {
        
        // 1. SurveyEntity 업데이트
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

        if (survey.getSurveyTitle() != null) {
            survey.setSurveyTitle(request.getSurveyTitle());
        }

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
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

        surveyRepository.delete(survey);
        return 1;
    }
}
