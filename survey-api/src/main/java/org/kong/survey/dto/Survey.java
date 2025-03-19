package org.kong.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    private int surveyId;
    private String surveyTitle;
    private String surveyVersion;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean usedYn;

    @Getter
    @Setter
    public static class Request {
        private int surveyId;
        private String surveyTitle;
        private boolean usedYn;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Integer surveyId;
        private String surveyTitle;
        private String surveyVersion;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private boolean usedYn;
    }

}
