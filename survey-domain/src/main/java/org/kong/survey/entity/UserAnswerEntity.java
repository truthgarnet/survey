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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAnswerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userAnswerId;

    private String userAnswer;

    private LocalDateTime answerDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Builder
    public UserAnswerEntity(Integer userAnswerId, String userAnswer,
                            LocalDateTime answerDate, UserEntity user, QuestionEntity question) {
        this.userAnswerId = userAnswerId;
        this.userAnswer = userAnswer;
        this.answerDate = answerDate;
        this.user = user;
        this.question = question;
    }
}
