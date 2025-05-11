package org.kong.survey.dto;

import lombok.*;
import org.kong.survey.entity.QuestionType;

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
    public static class Request {
        private Integer questionId;
        private QuestionType questionType;
        private String question;
        private int order;
        private boolean isRequired;
        List<SurveyAnswer.Request> answers;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private QuestionType questionType;
        private String question;
        private int questionOrder;
        private List<SurveyAnswer.Response> answers;
    }

}
