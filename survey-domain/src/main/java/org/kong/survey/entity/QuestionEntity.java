package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_QUESTION")
@Getter
@DynamicUpdate
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    @Column(length = 1000)
    private String question;

    @Column
    private int questionOrder;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;
}
