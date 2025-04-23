package org.kong.survey.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.kong.user.dto.User;

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
        private int surveyAnswerId;
        private int userId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int questionId;
        private String question;
        private int answerId;
        private String answer;
        private int surveyAnswerId;
        private int userAnswerId;
        private String userAnswer;
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
