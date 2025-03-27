package org.kong.survey.mapper;

import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.SurveyEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyMapperImpl implements SurveyMapper {
    @Override
    public Survey.Response toSurveyResponse(SurveyEntity surveyEntity) {
        if (surveyEntity == null) {
            return null;
        }

        // SurveyEntity -> Survey.Response 변환
        return Survey.Response.builder()
                .surveyId(surveyEntity.getSurveyId())
                .surveyTitle(surveyEntity.getSurveyTitle())
                .surveyVersion(surveyEntity.getSurveyVersion())
                .usedYn(Boolean.valueOf(surveyEntity.getUsedYn()))
                .createdDate(surveyEntity.getCreatedDate())
                .updatedDate(surveyEntity.getUpdatedDate())
                .questions(null)
                .build();
    }

    @Override
    public List<SurveyFindAll.Response> toSurveyFindAll(List<SurveyEntity> surveyList) {
        if (surveyList == null || surveyList.isEmpty()) {
            return List.of();
        }

        return surveyList.stream()
                .map(survey -> SurveyFindAll.Response.builder()
                        .surveyId(survey.getSurveyId())
                        .surveyTitle(survey.getSurveyTitle())
                        .surveyVersion(survey.getSurveyVersion())
                        .createdDate(survey.getCreatedDate())
                        .updatedDate(survey.getUpdatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public SurveyEntity toSurveyEntity(Survey.Request survey) {
        if (survey == null) {
            return null;
        }

        return SurveyEntity.builder()
                .surveyId(survey.getSurveyId())
                .surveyTitle(survey.getSurveyTitle())
                .surveyVersion(survey.getSurveyVersion())
                .usedYn(survey.isUsedYn()) // boolean 값 그대로 사용
                .build();
    }
}
