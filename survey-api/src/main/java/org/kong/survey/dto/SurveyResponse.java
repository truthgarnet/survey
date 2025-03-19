package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {

    private int responseId;
    private Survey survey;
    private String response;

    @Getter
    @Setter
    public static class Request {
        private String response;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Survey survey;
        private String response;
    }

}
