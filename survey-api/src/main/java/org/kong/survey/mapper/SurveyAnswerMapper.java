package org.kong.survey.mapper;

import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.SurveyAnswerEntity;
import org.kong.survey.entity.SurveyEntity;


public interface SurveyAnswerMapper {

     SurveyAnswerEntity toSurveyAnswerEntity(SurveyEntity survey, SurveyAnswer.Request surveyAnswerRequest);
}
