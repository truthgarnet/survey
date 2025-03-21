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
        private String question;
        private int order;
        List<SurveyAnswer.Request> answers;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private QuestionType questionType;
        private String question;
        private int order;
        private List<SurveyAnswer.Response> answers;
    }

}
