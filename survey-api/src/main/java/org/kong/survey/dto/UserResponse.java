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
public class UserResponse {

    private int userResponseId;
    private Survey survey;
    private User user;
    private Response response;
    private LocalDateTime responseDate;

    @Getter
    @Setter
    public static class Request {
        private int userId;
        private int surveyId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int questionId;
        private int answerId;
        private String response;
        private Date responseDate;
    }
}
