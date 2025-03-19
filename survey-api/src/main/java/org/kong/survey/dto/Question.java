package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private int questionId;
    private Survey survey;
    private QuestionType questionType;
    private String question;
    private int order;

    @Getter
    @Setter
    public static class Request {
        private QuestionType questionType;
        private List<String> question;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Survey survey;
        private QuestionType questionType;
        private String question;
        private int order;
    }

}
