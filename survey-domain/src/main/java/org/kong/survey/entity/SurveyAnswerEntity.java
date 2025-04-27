package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Builder
    public SurveyAnswerEntity(Integer answerId, String answer, QuestionEntity question) {
        this.answerId = answerId;
        this.answer = answer;
        this.question = question;
    }
}
