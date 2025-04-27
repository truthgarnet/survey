package org.kong.survey.repository;

import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {

    @Query("SELECT ua FROM UserAnswerEntity ua WHERE ua.question IN :questions AND ua.user.userId = :userId")
    List<UserAnswerEntity> findByQuestionAndUser_UserId(@Param("questions") List<QuestionEntity> questionEntityList,
            @Param("userId") Integer userId);
}
