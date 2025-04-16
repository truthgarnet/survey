package org.kong.survey.repository;

import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {

    List<UserAnswerEntity> findByQuestionAndUserId(List<QuestionEntity> questionEntityList, Integer userId);
}
