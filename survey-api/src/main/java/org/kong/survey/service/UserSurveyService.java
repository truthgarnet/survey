package org.kong.survey.service;

import lombok.RequiredArgsConstructor;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.mapper.UserAnswerMapper;
import org.kong.survey.repository.SurveyAnswerRepository;
import org.kong.survey.repository.SurveyRepository;
import org.kong.survey.repository.UserAnswerRepository;
import org.kong.user.dto.User;
import org.kong.user.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSurveyService {

    private final SurveyRepository surveyRepository;
    
    private final UserAnswerRepository userAnswerRepository;
    
    private final SurveyAnswerRepository surveyAnswerRepository;

    private final UserMapper userMapper;

    private final SurveyMapper surveyMapper;

    private final QuestionMapper questionMapper;

    private final UserAnswerMapper userAnswerMapper;

    public PageDto findListByUserSurveyId(int page, int size, int userId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> userSurveyList = surveyRepository.findByUserId(pageRequest, userId);

        PageDto surveyUserList = surveyMapper.toSurveyFindAll(userSurveyList);

        return surveyUserList;
    }

    public UserAnswer.SurveyResponse findUserSurvey(Survey.Response survey, User.Response user) {
        List<QuestionEntity> questionEntities = questionMapper.toQuestionEntityList(survey.getQuestions());
        List<UserAnswerEntity> userAnswerEntity = userAnswerRepository.findByQuestionAndUserId(questionEntities, user.getUserId());
        UserAnswer.SurveyResponse userAnswer = userAnswerMapper.toUserAnswerResponse(userAnswerEntity);

        return userAnswer;
    }

    public UserAnswer.SurveyResponse addSurvey(Integer surveyId, User.Response user, List<UserAnswer.Request> request) {
        UserAnswer.SurveyResponse userAnswer = new UserAnswer.SurveyResponse();
        if (!CollectionUtils.isEmpty(request)) {
            List<UserAnswerEntity> userAnswerEntities = userAnswerMapper.toUserAnswerEntity(request);
            userAnswerRepository.saveAll(userAnswerEntities);

             userAnswer = userAnswerMapper.toUserAnswerResponse(userAnswerEntities);
        }

        return userAnswer;
    }
}
