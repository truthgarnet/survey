package org.kong.survey.dto;

import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {

    private int userAnswerId;
    private Survey survey;
    private User user;
    private SurveyAnswer answer;
    private LocalDateTime answerDate;

    @Getter
    public static class Request {
        private int questionId;
        private int answerId;
        private String userAnswer;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int questionId;
        private int answerId;
        private String question;
        private String answer;
        private Date answerDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class SurveyResponse {
        private Integer surveyId;
        private String surveyTitle;
        private List<Response> userAnswer;
    }
}
