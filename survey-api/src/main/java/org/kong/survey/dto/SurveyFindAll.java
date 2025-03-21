package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SurveyFindAll {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private LocalDateTime createdDate;
    }

}
