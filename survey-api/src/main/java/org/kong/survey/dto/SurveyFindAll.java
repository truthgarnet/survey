package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SurveyFindAll {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Integer surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }

}
