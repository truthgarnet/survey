package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.SurveyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

    private final SurveyRepository surveyRepository;

    private final SurveyMapper surveyMapper;


    public Page<SurveyEntity> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> surveyList = surveyRepository.findAll(pageRequest);

        return surveyList;
    }

    public SurveyEntity findBySurveyId(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        return survey;
    }

    public Page<SurveyEntity> findListByUserSurveyId(int page, int size, int userId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> userSurveyList = surveyRepository.findByUserId(pageRequest, userId);

        return userSurveyList;
    }

    public SurveyEntity add(Survey.Request request) {
        // 1. Survey Add
        SurveyEntity survey = surveyMapper.toSurveyEntity(request);

        if (survey == null) throw new RuntimeException("설문지를 찾을 수 없습니다.");

        survey = surveyRepository.save(survey);

        return survey;
    }

    public SurveyEntity updateAll(Integer surveyId, Survey.Request request) {
        surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        SurveyEntity changeSurvey = surveyMapper.toSurveyEntityUpdate(surveyId, request);
        surveyRepository.save(changeSurvey);

        return changeSurvey;
    }

    public SurveyEntity updatePart(int surveyId, Survey.Request request) {
        
        // 1. SurveyEntity 업데이트
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        // 1-1. 빈칸 체크
        survey.updateCheckBlank(survey.getSurveyTitle(), survey.getSurveyVersion(), survey.getUsedYn());

        SurveyEntity changeSurvey = surveyMapper.toSurveyEntityUpdate(surveyId, request);

        surveyRepository.save(changeSurvey);
        return changeSurvey;
    }

    public boolean delete(Integer surveyId) {
        SurveyEntity survey = surveyRepository.findBySurveyId(surveyId).orElseThrow(() -> new RuntimeException("설문지를 찾을 수 없습니다."));

        surveyRepository.delete(survey);
        return true;
    }
}
