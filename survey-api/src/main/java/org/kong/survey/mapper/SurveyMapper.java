package org.kong.survey.mapper;

import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.SurveyEntity;
import java.util.List;

public interface SurveyMapper {

    // SurveyEntity -> Survey.Response
    Survey.Response toSurveyResponse(SurveyEntity surveyEntity);

    // List<SurveyEntity> -> List<SurveyFindAll.Response>
    List<SurveyFindAll.Response> toSurveyFindAll(List<SurveyEntity> surveyList);

    SurveyEntity toSurveyEntity(Survey.Request survey);
}
