package org.kong.survey.repository;

import jakarta.transaction.Transactional;
import org.kong.survey.entity.SurveyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    List<SurveyEntity> findAll();

    Page<SurveyEntity> findAll(Pageable pageable);

    Optional<SurveyEntity> findBySurveyId(Integer surveyId);

    Page<SurveyEntity> findByUserId(Pageable pageable, Integer userId);

    boolean existsBySurveyId(Integer surveyId);

    SurveyEntity save(SurveyEntity survey);

    @Transactional
    void deleteBySurveyId(Integer surveyId);
}
