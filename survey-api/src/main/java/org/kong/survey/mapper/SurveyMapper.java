package org.kong.survey.mapper;

import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.SurveyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);

    // SurveyEntity -> Survey.Response
    @Mapping(source = "responses", target = "questions")
    Survey.Response toSurveyResponse(SurveyEntity surveyEntity, List<Question.Response> responses);

    // List<SurveyEntity> -> List<SurveyFindAll.Response>
    PageDto toSurveyFindAll(Page<SurveyEntity> surveyList);

    @Mapping(source = "surveyId", target = "surveyId")
    SurveyEntity toSurveyEntityUpdate(Integer surveyId, Survey.Request survey);

    SurveyEntity toSurveyEntity(Survey.Request survey);

    List<SurveyFindAll.Response> toSurveyEntityList(List<SurveyEntity> surveyEntities);

}
