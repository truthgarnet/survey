package userSurvey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.facade.UserSurveyFacade;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.mapper.UserAnswerMapper;
import org.kong.survey.service.QuestionService;
import org.kong.survey.service.SurveyService;
import org.kong.survey.service.UserAnswerService;
import org.kong.user.entity.UserEntity;
import org.kong.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSurveyFacadeTest {

    @InjectMocks
    private UserSurveyFacade userSurveyFacade;

    @Mock
    private SurveyService surveyService;

    @Mock
    private UserService userService;

    @Mock
    private UserAnswerService userAnswerService;

    @Mock
    private QuestionService questionService;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private UserAnswerMapper userAnswerMapper;

    @Test
    void testFindListByUserSurveyId() {
        // given
        int page = 0, size = 10, userId = 1;
        Page<SurveyEntity> surveyPage = new PageImpl<>(List.of(new SurveyEntity()));
        PageDto pageDto = new PageDto();

        when(surveyService.findListByUserSurveyId(page, size, userId)).thenReturn(surveyPage);
        when(surveyMapper.toSurveyFindAll(surveyPage)).thenReturn(pageDto);

        // when
        PageDto result = userSurveyFacade.findListByUserSurveyId(page, size, userId);

        // then
        assertThat(pageDto).isEqualTo(result);
        verify(surveyService).findListByUserSurveyId(page, size, userId);
        verify(surveyMapper).toSurveyFindAll(surveyPage);
    }

    @Test
    void testFindUserSurvey() {
        // given
        Integer surveyId = 1, userId = 1;

        SurveyEntity surveyEntity = new SurveyEntity();
        UserEntity userEntity = new UserEntity();
        List<QuestionEntity> questionEntities = List.of(new QuestionEntity());
        List<UserAnswerEntity> userAnswerEntities = List.of(new UserAnswerEntity());
        UserAnswer.SurveyResponse response = new UserAnswer.SurveyResponse();

        when(userService.findUserById(userId)).thenReturn(userEntity);
        when(surveyService.findBySurveyId(surveyId)).thenReturn(surveyEntity);
        when(questionService.findBySurvey(surveyId)).thenReturn(questionEntities);
        when(userAnswerService.findUserAnswers(questionEntities, userId)).thenReturn(userAnswerEntities);
        when(userAnswerMapper.toUserSurveyResponse(surveyEntity, questionEntities, userAnswerEntities)).thenReturn(response);

        // when
        UserAnswer.SurveyResponse result = userSurveyFacade.findUserSurvey(surveyId, userId);

        // then
        assertThat(response).isEqualTo(result);
    }

    @Test
    void testAddSurvey() {
        // given
        Integer userId = 1;
        List<UserAnswer.Request> requestList = List.of(new UserAnswer.Request());
        List<UserAnswerEntity> answerEntities = List.of(new UserAnswerEntity());
        UserAnswer.SurveyResponse response = new UserAnswer.SurveyResponse();
        UserEntity userEntity = new UserEntity();

        when(userService.findUserById(userId)).thenReturn(userEntity);
        when(userAnswerMapper.toUserAnswerEntity(requestList)).thenReturn(answerEntities);
        when(userAnswerService.addSurvey(answerEntities)).thenReturn(answerEntities);
        when(userAnswerMapper.toUserAnswerResponse(answerEntities)).thenReturn(response);

        // when
        UserAnswer.SurveyResponse result = userSurveyFacade.addSurvey(requestList);

        // then
        assertThat(response).isEqualTo(result);
    }
}
