package userSurvey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.QuestionType;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.mapper.UserAnswerMapper;
import org.kong.survey.repository.SurveyAnswerRepository;
import org.kong.survey.repository.SurveyRepository;
import org.kong.survey.repository.UserAnswerRepository;
import org.kong.survey.service.UserSurveyService;
import org.kong.user.dto.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private UserAnswerRepository userAnswerRepository;

    @Mock
    private SurveyAnswerRepository surveyAnswerRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private UserAnswerMapper userAnswerMapper;

    @InjectMocks
    private UserSurveyService userSurveyService;

    @Test
    void testFindListByUserSurveyId() {
        int page = 0;
        int size = 5;
        int userId = 1;

        PageRequest pageRequest = PageRequest.of(page, size);
        SurveyEntity surveyEntity = SurveyEntity.builder()
                .surveyId(1)
                .surveyTitle("건강 설문조사")
                .surveyVersion("1V")
                .usedYn(true)
                .build();

        List<SurveyEntity> surveyEntities = List.of(surveyEntity);
        Page<SurveyEntity> surveyPage = new PageImpl<>(surveyEntities, pageRequest, surveyEntities.size());

        PageDto expectedDto = new PageDto();

        when(surveyRepository.findByUserId(pageRequest, userId)).thenReturn(surveyPage);
        when(surveyMapper.toSurveyFindAll(surveyPage)).thenReturn(expectedDto);

        PageDto result = userSurveyService.findListByUserSurveyId(page, size, userId);

        assertEquals(expectedDto, result);
        verify(surveyRepository).findByUserId(pageRequest, userId);
        verify(surveyMapper).toSurveyFindAll(surveyPage);
    }

    @Test
    void testFindUserSurvey() {
        Survey.Response survey = new Survey.Response();
        User.Response user = new User.Response(1, "이름", "닉네임");

        QuestionEntity question = QuestionEntity.builder()
                .questionId(1)
                .question("하루에 몇 시간 운동하시나요?")
                .questionType(QuestionType.SUB)
                .questionOrder(1)
                .build();
        List<QuestionEntity> questionEntities = List.of(question);

        UserAnswerEntity userAnswerEntity = UserAnswerEntity.builder()
                .userAnswerId(1)
                .userAnswer("2시간")
                .answerDate(LocalDateTime.now())
                .build();
        List<UserAnswerEntity> userAnswerEntities = List.of(userAnswerEntity);
        UserAnswer.SurveyResponse expectedResponse = new UserAnswer.SurveyResponse();

        when(questionMapper.toQuestionEntityList(survey.getQuestions())).thenReturn(questionEntities);
        when(userAnswerRepository.findByQuestionAndUserId(questionEntities, user.getUserId())).thenReturn(userAnswerEntities);
        when(userAnswerMapper.toUserAnswerResponse(userAnswerEntities)).thenReturn(expectedResponse);

        UserAnswer.SurveyResponse result = userSurveyService.findUserSurvey(survey, user);

        assertEquals(expectedResponse, result);
        verify(questionMapper).toQuestionEntityList(survey.getQuestions());
        verify(userAnswerRepository).findByQuestionAndUserId(questionEntities, user.getUserId());
        verify(userAnswerMapper).toUserAnswerResponse(userAnswerEntities);
    }

    @Test
    void testAddSurvey() {
        Integer surveyId = 1;
        User.Response user = new User.Response(1, "이름", "닉네임");

        List<UserAnswer.Request> requestList = List.of(new UserAnswer.Request());

        UserAnswerEntity userAnswerEntity = UserAnswerEntity.builder()
                .userAnswerId(1)
                .userAnswer("2시간")
                .answerDate(LocalDateTime.now())
                .build();
        List<UserAnswerEntity> userAnswerEntities = List.of(userAnswerEntity);
        UserAnswer.SurveyResponse expectedResponse = new UserAnswer.SurveyResponse();

        when(userAnswerMapper.toUserAnswerEntity(requestList)).thenReturn(userAnswerEntities);
        when(userAnswerMapper.toUserAnswerResponse(userAnswerEntities)).thenReturn(expectedResponse);

        UserAnswer.SurveyResponse result = userSurveyService.addSurvey(surveyId, user, requestList);

        assertEquals(expectedResponse, result);
        verify(userAnswerMapper).toUserAnswerEntity(requestList);
        verify(userAnswerRepository).saveAll(userAnswerEntities);
        verify(userAnswerMapper).toUserAnswerResponse(userAnswerEntities);
    }

    @Test
    void testAddSurveyWithEmptyRequest() {
        Integer surveyId = 1;
        User.Response user = new User.Response(1, "이름", "닉네임");
        List<UserAnswer.Request> requestList = new ArrayList<>();

        UserAnswer.SurveyResponse result = userSurveyService.addSurvey(surveyId, user, requestList);

        assertNotNull(result);
        verify(userAnswerMapper, never()).toUserAnswerEntity(any());
        verify(userAnswerRepository, never()).saveAll(any());
    }
}