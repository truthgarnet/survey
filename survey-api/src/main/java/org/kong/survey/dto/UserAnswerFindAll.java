package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserAnswerFindAll {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private int surveyId;
        private String surveyTitle;
        private String surveyVersion;
    }

}
