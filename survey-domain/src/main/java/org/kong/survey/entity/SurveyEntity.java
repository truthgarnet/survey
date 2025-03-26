package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SURVEY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer surveyId;

    private String surveyTitle;

    private String surveyVersion;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Boolean usedYn;


    @Builder
    public SurveyEntity(Integer surveyId, String surveyTitle, String surveyVersion,
                  LocalDateTime createdDate, LocalDateTime updatedDate, Boolean usedYn) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.usedYn = usedYn;
    }

}
