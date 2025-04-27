package org.kong.survey.facade;

import lombok.RequiredArgsConstructor;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.mapper.UserAnswerMapper;
import org.kong.survey.service.QuestionService;
import org.kong.survey.service.SurveyService;
import org.kong.survey.service.UserAnswerService;
import org.kong.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSurveyFacade {

    private final SurveyService surveyService;
    private final UserService userService;
    private final UserAnswerService userAnswerService;
    private final QuestionService questionService;

    private final SurveyMapper surveyMapper;
    private final UserAnswerMapper userAnswerMapper;

    public PageDto findListByUserSurveyId(int page, int size) {
        Page<SurveyEntity> userSurveyList = surveyService.findAll(page, size);

        PageDto surveyUserList = surveyMapper.toSurveyFindAll(userSurveyList);
        return surveyUserList;
    }

    public UserAnswer.SurveyResponse findUserSurvey(Integer surveyId, Integer userId) {
        // @TODO: 토큰 적용시 변경 예정
        userService.findUserById(userId); // 사용자 정보 확인

        SurveyEntity surveyEntity = surveyService.findBySurveyId(surveyId); // 설문지 정보

        List<QuestionEntity> questionEntities = questionService.findBySurvey(surveyId);

        List<UserAnswerEntity> userAnswerEntities = userAnswerService.findUserAnswers(questionEntities, userId);

        UserAnswer.SurveyResponse userAnswer = userAnswerMapper.toUserSurveyResponse(surveyEntity, questionEntities, userAnswerEntities);

        return userAnswer;
    }

    public UserAnswer.SurveyResponse addSurvey(List<UserAnswer.Request> request) {
        // @TODO: 토큰 적용시 변경 예정
        Integer userId = 1;
        userService.findUserById(userId);

        List<UserAnswerEntity> userAnswerEntities = userAnswerMapper.toUserAnswerEntity(request);
        List<UserAnswerEntity> saveUserAnswers = userAnswerService.addSurvey(userAnswerEntities);
        UserAnswer.SurveyResponse userAnswer = userAnswerMapper.toUserAnswerResponse(saveUserAnswers);

        return userAnswer;
    }


}
