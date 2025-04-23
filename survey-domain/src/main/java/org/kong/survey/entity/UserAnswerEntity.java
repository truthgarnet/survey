package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.kong.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER_ANSWER")
@Getter
@DynamicUpdate
@NoArgsConstructor
public class UserAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userAnswerId;

    private Integer surveyAnswerId;

    private String userAnswer;

    private LocalDateTime answerDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Builder
    public UserAnswerEntity(Integer userAnswerId, Integer surveyAnswerId, String userAnswer,
            LocalDateTime answerDate, UserEntity user, QuestionEntity question) {
        this.userAnswerId = userAnswerId;
        this.surveyAnswerId = surveyAnswerId;
        this.userAnswer = userAnswer;
        this.answerDate = answerDate;
        this.user = user;
        this.question = question;
    }
}
