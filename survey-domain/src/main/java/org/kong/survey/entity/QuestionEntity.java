package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TB_QUESTION")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    @Column(length = 1000)
    private String question;

    @Column
    private QuestionType questionType;

    @Column
    private int questionOrder;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    @Builder
    public QuestionEntity(Integer questionId, String question, QuestionType questionType, int questionOrder, SurveyEntity survey) {
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
        this.questionOrder = questionOrder;
        this.survey = survey;
    }
}
