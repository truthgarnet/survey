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
        List<SurveyAnswer.Request> answers;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private QuestionType questionType;
        private String question;
        private int order;
        private List<SurveyAnswer.Response> answers;
    }

}
