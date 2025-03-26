package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_SURVEY_ANSWER")
@Getter
@DynamicUpdate
public class SurveyAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer answerId;

    @Column(length = 1000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;
}
