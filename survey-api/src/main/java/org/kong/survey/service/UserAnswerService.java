package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public List<UserAnswerEntity> findUserAnswers(List<QuestionEntity> questionEntities, Integer userId) {
        List<UserAnswerEntity> userAnswerEntities = userAnswerRepository.findByQuestionAndUser_UserId(questionEntities,
                userId);
        return userAnswerEntities;
    }

    public List<UserAnswerEntity> addSurvey(List<UserAnswerEntity> userAnswerEntities) {
        List<UserAnswerEntity> saveEntities = userAnswerRepository.saveAll(userAnswerEntities);

        return saveEntities;
    }

}
