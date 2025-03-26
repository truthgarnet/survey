package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SURVEY")
@Getter
@DynamicUpdate
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer surveyId;

    private String surveyTitle;

    private String surveyVersion;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private boolean usedYn;
}
