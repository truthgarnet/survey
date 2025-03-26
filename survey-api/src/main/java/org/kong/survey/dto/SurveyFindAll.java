package org.kong.survey.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class SurveyFindAll {

    @Getter
    @Builder
    public static class Response {
        private int surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private LocalDateTime createdDate;
    }

}
