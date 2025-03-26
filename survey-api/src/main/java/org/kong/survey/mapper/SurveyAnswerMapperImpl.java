package org.kong.survey.mapper;

import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.SurveyAnswerEntity;
import org.kong.survey.entity.SurveyEntity;
import org.springframework.stereotype.Component;

@Component
public class SurveyAnswerMapperImpl implements SurveyAnswerMapper {
    @Override
    public SurveyAnswerEntity toSurveyAnswerEntity(SurveyEntity survey, SurveyAnswer.Request surveyAnswerRequest) {
        if (surveyAnswerRequest == null) {
            return null;
        }

        return SurveyAnswerEntity.builder()
                .answer(surveyAnswerRequest.getAnswer())
                .survey(survey)
                .build();
    }
}
