package org.kong.survey.repository;

import jakarta.transaction.Transactional;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.SurveyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    List<SurveyEntity> findAll();

    Page<SurveyEntity> findAll(Pageable pageable);

    Optional<SurveyEntity> findBySurveyId(Integer surveyId);

    SurveyEntity save(SurveyEntity survey);

    @Modifying
    @Transactional
    @Query("UPDATE SurveyEntity s SET s.status = :status WHERE s.surveyId = :surveyId")
    void updateSurveyStatus(@Param("surveyId") Integer surveyId, @Param("status") SurveyStatus status);
}
