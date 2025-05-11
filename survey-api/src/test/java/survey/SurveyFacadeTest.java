package survey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.facade.SurveyFacade;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.service.QuestionService;
import org.kong.survey.service.SurveyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyFacadeTest {

    @InjectMocks
    private SurveyFacade surveyFacade;

    @Mock
    private SurveyService surveyService;

    @Mock
    private QuestionService questionService;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private QuestionMapper questionMapper;

    @Test
    @DisplayName("정상적인 접근 - 모든 설문지 조회")
    void findAllSurvey_Success() {
        // given
        int page = 0, size = 10;
        Page<SurveyEntity> mockPage = new PageImpl<>(List.of(new SurveyEntity()));
        PageDto pageDto = new PageDto();

        when(surveyService.findAll(page, size)).thenReturn(mockPage);
        when(surveyMapper.toSurveyFindAll(mockPage)).thenReturn(pageDto);

        // when
        String search = "title";
        PageDto result = surveyFacade.findAllSurvey(page, size, search);

        // then
        assertThat(pageDto).isEqualTo(result);
        verify(surveyService).findAll(page, size);
        verify(surveyMapper).toSurveyFindAll(mockPage);
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 단건 조회")
    void findBySurveyId_Success() {
        // given
        Integer surveyId = 1;
        SurveyEntity surveyEntity = new SurveyEntity();
        List<QuestionEntity> questionEntities = List.of(new QuestionEntity());
        List<Question.Response> questionResponses = List.of(new Question.Response());
        Survey.Response expectedResponse = new Survey.Response();

        when(surveyService.findBySurveyId(surveyId)).thenReturn(surveyEntity);
        when(questionService.findBySurvey(surveyId)).thenReturn(questionEntities);
        when(questionMapper.toQuestionResponseList(questionEntities)).thenReturn(questionResponses);
        when(surveyMapper.toSurveyResponse(surveyEntity, questionResponses)).thenReturn(expectedResponse);

        // when
        Survey.Response result = surveyFacade.findBySurveyId(surveyId);

        // then
        assertThat(expectedResponse).isEqualTo(result);
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 추가 하기")
    void add_Success() {
        // given
        Survey.Request request = new Survey.Request();
        request.setQuestions(List.of(new Question.Request()));

        SurveyEntity surveyEntity = new SurveyEntity();
        List<QuestionEntity> questionEntities = List.of(new QuestionEntity());
        List<Question.Response> questionResponses = List.of(new Question.Response());
        Survey.Response expectedResponse = new Survey.Response();

        when(surveyService.add(request)).thenReturn(surveyEntity);
        when(questionMapper.toQuestionEntityList(surveyEntity, request.getQuestions())).thenReturn(questionEntities);
        when(questionService.addAll(questionEntities)).thenReturn(null);
        when(questionMapper.toQuestionResponseList(questionEntities)).thenReturn(questionResponses);
        when(surveyMapper.toSurveyResponse(surveyEntity, questionResponses)).thenReturn(expectedResponse);

        // when
        Survey.Response result = surveyFacade.add(request);

        // then
        assertThat(expectedResponse).isEqualTo(result);
    }

    @Test
    @DisplayName("정상적인 접근 - 모든 수정")
    void updateAll_Success() {
        // given
        Integer surveyId = 1;
        Survey.Request request = mock(Survey.Request.class);
        List<Question.Request> questionRequests = List.of(mock(Question.Request.class));

        SurveyEntity updatedSurvey = mock(SurveyEntity.class);
        List<QuestionEntity> questionEntities = List.of(mock(QuestionEntity.class));
        List<Question.Response> questionResponses = List.of(mock(Question.Response.class));
        Survey.Response expectedResponse = mock(Survey.Response.class);

        when(request.getQuestions()).thenReturn(questionRequests);
        when(surveyService.updateAll(surveyId, request)).thenReturn(updatedSurvey);
        when(questionMapper.toQuestionEntityList(updatedSurvey, questionRequests)).thenReturn(questionEntities);
        when(questionService.updateAll(questionEntities)).thenReturn(questionEntities);
        when(questionMapper.toQuestionResponseList(questionEntities)).thenReturn(questionResponses);
        when(surveyMapper.toSurveyResponse(updatedSurvey, questionResponses)).thenReturn(expectedResponse);

        // when
        Survey.Response result = surveyFacade.updateAll(surveyId, request);

        // then
        assertThat(expectedResponse).isEqualTo(result);
        verify(surveyService).updateAll(surveyId, request);
        verify(questionService).updateAll(questionEntities);
        verify(surveyMapper).toSurveyResponse(updatedSurvey, questionResponses);
    }

    @Test
    @DisplayName("정상적인 접근 - 일부 수정")
    void updatePart_Success() {
        // given
        Integer surveyId = 1;
        Survey.Request request = mock(Survey.Request.class);
        List<Question.Request> questionRequests = List.of(mock(Question.Request.class));

        SurveyEntity updatedSurvey = mock(SurveyEntity.class);
        List<QuestionEntity> questionEntities = List.of(mock(QuestionEntity.class));
        List<Question.Response> questionResponses = List.of(mock(Question.Response.class));
        Survey.Response expectedResponse = mock(Survey.Response.class);

        when(request.getQuestions()).thenReturn(questionRequests);
        when(surveyService.updatePart(surveyId, request)).thenReturn(updatedSurvey);
        when(questionMapper.toQuestionEntityList(updatedSurvey, questionRequests)).thenReturn(questionEntities);
        when(questionService.updateAll(questionEntities)).thenReturn(questionEntities);
        when(questionMapper.toQuestionResponseList(questionEntities)).thenReturn(questionResponses);
        when(surveyMapper.toSurveyResponse(updatedSurvey, questionResponses)).thenReturn(expectedResponse);

        // when
        Survey.Response result = surveyFacade.updatePart(surveyId, request);

        // then
        assertThat(expectedResponse).isEqualTo(result);
        verify(surveyService).updatePart(surveyId, request);
        verify(questionService).updateAll(questionEntities);
        verify(surveyMapper).toSurveyResponse(updatedSurvey, questionResponses);
    }


    @Test
    @DisplayName("정상적인 접근 - 삭제하기")
    void delete_Success() {
        // given
        int surveyId = 1;
        when(surveyService.delete(surveyId)).thenReturn(true);

        // when
        Object result = surveyFacade.delete(surveyId);

        // then
        assertThat(1).isEqualTo(result);
    }
}