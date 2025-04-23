package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SURVEY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surveyId;

    private String surveyTitle;

    private String surveyVersion;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    private Boolean usedYn;


    @Builder
    public SurveyEntity(Integer surveyId, String surveyTitle, String surveyVersion, Boolean usedYn) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.usedYn = usedYn;
    }

    public void updateCheckBlank(String surveyTitle, String surveyVersion, boolean usedYn) {
        if (surveyTitle != null) {
            this.surveyTitle = surveyTitle;
        }

        if (surveyVersion != null) {
            this.surveyVersion = surveyVersion;
        }

    }

}
