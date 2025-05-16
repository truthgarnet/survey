package org.kong.survey.facade;

import lombok.RequiredArgsConstructor;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.service.QuestionService;
import org.kong.survey.service.SurveyService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyFacade {

    private final SurveyService surveyService;
    private final QuestionService questionService;

    private final SurveyMapper surveyMapper;
    private final QuestionMapper questionMapper;

    public PageDto findAllSurvey(int page, int size, String search) {
        Page<SurveyEntity> surveyList;
        if (search != null && !search.trim().isEmpty()) {
            surveyList = surveyService.findBySurveyTitle(page, size, search);
        } else {
            surveyList = surveyService.findAll(page, size);
        }
        PageDto surveyFindAll = surveyMapper.toSurveyFindAll(surveyList);

        return surveyFindAll;
    }

    public Survey.Response findBySurveyId(Integer surveyId) {
        SurveyEntity survey = surveyService.findBySurveyId(surveyId);
        List<QuestionEntity> questionEntities = questionService.findBySurvey(surveyId);

        List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(questionEntities);

        return surveyMapper.toSurveyResponse(survey, questionResponses);
    }

    public Survey.Response add(Survey.Request request) {
        // 설문지 추가
        SurveyEntity survey = surveyService.add(request);

        // 질문지 추가
        List<QuestionEntity> questionEntities = questionMapper.toQuestionEntityList(survey, request.getQuestions());
        questionService.addAll(questionEntities);

        List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(questionEntities);

        return surveyMapper.toSurveyResponse(survey, questionResponses);
    }

    public Survey.Response updateAll(Integer surveyId, Survey.Request request) {
         SurveyEntity changeSurvey = surveyService.updateAll(surveyId, request);

         List<Question.Request> questions = request.getQuestions();
         List<QuestionEntity> changeQuestionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);

         List<QuestionEntity> changeQuestions = questionService.updateAll(changeQuestionEntities);
         List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(changeQuestions);

         return surveyMapper.toSurveyResponse(changeSurvey, questionResponses);
    }

    public Survey.Response updatePart(Integer surveyId, Survey.Request request) {
        SurveyEntity changeSurvey = surveyService.updatePart(surveyId, request);

        List<Question.Request> questions = request.getQuestions();
        List<QuestionEntity> changeQuestionEntities = questionMapper.toQuestionEntityList(changeSurvey, questions);

        List<QuestionEntity> changeQuestions = questionService.updateAll(changeQuestionEntities);
        List<Question.Response> questionResponses = questionMapper.toQuestionResponseList(changeQuestions);

        return surveyMapper.toSurveyResponse(changeSurvey, questionResponses);
    }

    public Object delete(int surveyId) {
        boolean deleteSurvey = surveyService.delete(surveyId);

        if (deleteSurvey) {
            return 1;
        }
        return 0;
    }

}
