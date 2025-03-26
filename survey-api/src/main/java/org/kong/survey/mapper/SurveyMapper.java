package org.kong.survey.mapper;

import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.SurveyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);

    // SurveyEntity -> Survey.Response
    Survey.Response toSurveyResponse(SurveyEntity surveyEntity);

    SurveyFindAll.Response toSurveyFindAll(SurveyEntity surveyEntity);

    // List<SurveyEntity> -> List<SurveyFindAll.Response>
    List<SurveyFindAll.Response> toSurveyFindAll(List<SurveyEntity> surveyList);

    // Survey.Request -> SurveyEntity
    SurveyEntity toSurveyEntity(Survey.Request survey);

}
