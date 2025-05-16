package org.kong.survey.repository;

import org.kong.survey.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findBySurvey_surveyId(Integer surveyId);
    
    Optional<QuestionEntity> findByQuestionId(Integer questionId);
}
