package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private String answer;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int answerId;
        private String answer;
    }

}
