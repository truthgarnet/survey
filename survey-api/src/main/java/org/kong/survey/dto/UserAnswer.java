package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.Date;

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
    @Setter
    public static class Request {
        private int questionId;
        private String userAnswer;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private int questionId;
        private int answerId;
        private String answer;
        private Date answerDate;
    }
}
