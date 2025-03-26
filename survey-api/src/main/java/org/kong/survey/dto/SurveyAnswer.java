package org.kong.survey.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswer {

    private int answerId;
    private Survey survey;
    private String answer;

    @Getter
    @Setter
    public static class Request {
        private int surveyId;
        private String answer;
    }

    @Getter
    @Builder
    public static class Response {
        private int answerId;
        private String answer;
    }

}
