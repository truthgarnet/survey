package org.kong.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.kong.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER_ANSWER")
@Getter
@DynamicUpdate
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
}
