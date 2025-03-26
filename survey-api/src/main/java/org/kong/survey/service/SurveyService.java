package org.kong.survey.service;

import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyMapper surveyMapper;

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

        survey = surveyRepository.save(survey);

        if (survey == null) {
            throw new RuntimeException();
        }

        return surveyMapper.toSurveyResponse(survey);
    }

    public Survey.Response updateAll(Integer surveyId, Survey.Request request) {
        surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

        SurveyEntity changeSurvey = surveyMapper.toSurveyEntity(request);
        surveyRepository.save(changeSurvey);

        return surveyMapper.toSurveyResponse(changeSurvey);
    }

    public Integer delete(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException());

        surveyRepository.delete(survey);
        return 1;
    }
}
