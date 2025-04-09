package org.kong.survey.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    private Integer surveyId;
    private String surveyTitle;
    private String surveyVersion;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean usedYn;

    @Builder
    @Getter
    public static class Request {
        private Integer surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private boolean usedYn;
        private List<Question.Request> questions;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private Boolean usedYn;
        private List<Question.Response> questions;
    }

}
