package org.kong.survey.mapper;

import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.SurveyAnswerEntity;
import org.kong.survey.entity.SurveyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper {

     SurveyAnswerMapper INSTANCE = Mappers.getMapper(SurveyAnswerMapper.class);

     @Mapping(source = "surveyAnswerRequest.answer", target = "answer")
     SurveyAnswerEntity toSurveyAnswerEntity(SurveyEntity survey, SurveyAnswer.Request surveyAnswerRequest);
}
