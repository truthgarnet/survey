package org.kong.survey.facade;

import lombok.RequiredArgsConstructor;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.service.SurveyService;
import org.kong.survey.service.UserSurveyService;
import org.kong.user.dto.User;
import org.kong.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyFacade {

    private final UserService userService;
    private final SurveyService surveyService;
    private final UserSurveyService userSurveyService;

    public UserAnswer.SurveyResponse findUserSurvey(int surveyId, int userId) {
        // @TODO: 토큰 적용시 변경 예정
        User.Response user = userService.findUserById(userId); // 사용자 정보
        Survey.Response survey = surveyService.findBySurveyId(surveyId); // 설문지 정보
        UserAnswer.SurveyResponse userAnswer = userSurveyService.findUserSurvey(survey, user);

        return userAnswer;
    }

    public UserAnswer.SurveyResponse addSurvey(Integer surveyId, List<UserAnswer.Request> request) {
        // @TODO: 토큰 적용시 변경 예정
        Integer userId = 1;
        User.Response user = userService.findUserById(userId);
        UserAnswer.SurveyResponse userAnswer = userSurveyService.addSurvey(surveyId, user, request);
        return userAnswer;
    }

}
