package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 1000)
    private String surveyTitle;

    @Column(length = 10)
    private String surveyVersion;

    @Enumerated(EnumType.STRING)
    private SurveyStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    private Boolean usedYn;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions = new ArrayList<>();

    @Builder
    public SurveyEntity(Integer surveyId, String surveyTitle, String surveyVersion, Boolean usedYn,
            SurveyStatus status) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.usedYn = usedYn;
        this.status = status;
    }

    public void updateCheckBlank(String surveyTitle, String surveyVersion, boolean usedYn) {
        if (surveyTitle != null) {
            this.surveyTitle = surveyTitle;
        }

        if (surveyVersion != null) {
            this.surveyVersion = surveyVersion;
        }
    }

    public SurveyStatus getSurveyStatus() {
        return this.status;
    }
}
