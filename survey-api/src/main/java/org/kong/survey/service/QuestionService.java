package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findBySurvey(Integer surveyId) {
        List<QuestionEntity> questionEntities = questionRepository.findBySurvey_surveyId(surveyId);

        return questionEntities;
    }

    public void addAll(List<QuestionEntity> questionEntities) {
        questionRepository.saveAll(questionEntities);
    }

    public List<QuestionEntity> updateAll(List<QuestionEntity> questionEntities) {
        List<QuestionEntity> changeQuestions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(questionEntities)) {
            changeQuestions = questionRepository.saveAll(questionEntities);
        }
        return changeQuestions;
    }

    public boolean delete(Integer surveyId) {
        List<QuestionEntity> questions = questionRepository.findBySurvey_surveyId(surveyId);

        questionRepository.deleteAll(questions);
        return true;
    }
}
