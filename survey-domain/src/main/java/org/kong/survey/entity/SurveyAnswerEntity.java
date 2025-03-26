package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SURVEY_ANSWER")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer answerId;

    @Column(length = 1000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    @Builder
    public SurveyAnswerEntity(Integer answerId, String answer, SurveyEntity survey) {
        this.answerId = answerId;
        this.answer = answer;
        this.survey = survey;
    }
}
