package unit;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.QuestionType;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.mapper.QuestionMapper;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.QuestionRepository;
import org.kong.survey.repository.SurveyRepository;
import org.kong.survey.service.SurveyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private SurveyService surveyService;

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 단건 조회")
    public void getSurvey_Exception() {
        // given
        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            surveyService.findBySurveyId(1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("설문지를 찾을 수 없습니다.");
    }
    
    @Test
    @DisplayName("정상적인 접근 - 설문지 단건 조회")
    public void getSurvey_Success() {
        // given
        Integer surveyId = 1;
        String surveyTitle = "설문지";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true);

        Survey.Response mockResponse = new Survey.Response(surveyId, surveyTitle, surveyVersion, LocalDateTime.now(), LocalDateTime.now(), true, null);
        List<Question.Response> responses = new ArrayList<>();

        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.of(mockitoSurvey));
        when(surveyMapper.toSurveyResponse(mockitoSurvey, responses)).thenReturn(mockResponse);

        // when
        Survey.Response result = surveyService.findBySurveyId(1);
        // then
        assertThat(mockitoSurvey).isNotNull();
        assertThat(result.getSurveyId()).isEqualTo(surveyId);
        assertThat(result.getSurveyTitle()).isEqualTo(surveyTitle);

        verify(surveyRepository, times(1)).findBySurveyId(surveyId);
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 리스트 조회")
    public void getSurveyList_Success() {
        // given
        List<SurveyEntity> surveyEntityList = new ArrayList<>();

        Integer surveyId = 1;
        String surveyTitle = "설문지1";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey1 = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true);
        surveyEntityList.add(mockitoSurvey1);

        Integer surveyId2 = 2;
        String surveyTitle2 = "설문지2";
        String surveyVersion2 = "2V";
        SurveyEntity mockitoSurvey2 = new SurveyEntity(surveyId2, surveyTitle2, surveyVersion2, true);
        surveyEntityList.add(mockitoSurvey2);

        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> surveyPages = new PageImpl<>(surveyEntityList, pageRequest, surveyEntityList.size());
        when(surveyRepository.findAll(pageRequest)).thenReturn(surveyPages);


        List<SurveyFindAll.Response> responseList = new ArrayList<>();
        responseList.add(new SurveyFindAll.Response(surveyId, surveyTitle, surveyVersion, LocalDateTime.now(), LocalDateTime.now()));
        responseList.add(new SurveyFindAll.Response(surveyId2, surveyTitle2, surveyVersion2, LocalDateTime.now(), LocalDateTime.now()));

        PageDto<SurveyFindAll.Response> expectedResponse = PageDto.<SurveyFindAll.Response>builder()
                .totalElements(surveyPages.getTotalElements())
                .totalPages(surveyPages.getTotalPages())
                .content(responseList)
                .build();


        when(surveyMapper.toSurveyFindAll(any(Page.class))).thenReturn(expectedResponse);

        // when
        PageDto<SurveyFindAll.Response> result = surveyService.findAll(page, size);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(SurveyFindAll.Response::getSurveyId)
                .isNotNull();
        assertThat(result.getContent())
                .extracting(SurveyFindAll.Response::getSurveyTitle)
                .containsExactly("설문지1", "설문지2");

        verify(surveyRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 삭제")
    public void delete_Exception() {
        // given
        SurveyEntity survey = new SurveyEntity(1, "설문지", "1V", true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            surveyService.delete(1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("설문지를 찾을 수 없습니다.");
    }
    
    @Test
    @DisplayName("정상적인 접근 - 설문지 삭제")
    public void delete_Success() {
        // given
        Integer surveyId = 1;
        String surveyTitle = "설문지";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true);

        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.of(mockitoSurvey));
        doNothing().when(surveyRepository).delete(mockitoSurvey);

        // when
        Integer result = surveyService.delete(surveyId);

        // then
        assertThat(result).isEqualTo(1);

        verify(surveyRepository, times(1)).delete(mockitoSurvey);
    }

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 수정")
    public void updateAll_Exception() {
        // given
        SurveyEntity changeSurvey = new SurveyEntity(1, "설문지", "1V", true);
        Survey.Request request = Survey.Request.builder().build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            surveyService.updateAll(1, request);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("설문지를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 수정")
    public void updateAll_Success() {
        // given
        Integer surveyId = 1;
        String surveyTitle = "설문지";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true);
        SurveyEntity updatedSurveyEntity = new SurveyEntity(2, "수정된설문지", "2V", false);

        List<Question.Request> requests = new ArrayList<>();
        List<QuestionEntity> questionEntities = new ArrayList<>();
        Survey.Request request = Survey.Request.builder()
                .surveyId(2)
                .surveyVersion("2V")
                .surveyTitle("수정된설문지")
                .questions(requests)
                .build();

        when(surveyRepository.findBySurveyId(surveyId)).thenReturn(Optional.of(mockitoSurvey));
        when(surveyMapper.toSurveyEntityUpdate(surveyId, request)).thenReturn(updatedSurveyEntity);
        when(questionMapper.toQuestionResponseList(any())).thenReturn(Collections.emptyList());
        when(questionMapper.toQuestionEntityList(any(), any())).thenReturn(List.of(new QuestionEntity(1, "질문입니다.",  QuestionType.FIVE, 1, mockitoSurvey)));
        when(surveyMapper.toSurveyResponse(any(), any())).thenReturn(new Survey.Response());

        // when
        Survey.Response result = surveyService.updateAll(surveyId, request);

        // then
        assertThat(result).isNotNull();

        verify(questionRepository, times(1)).save(any(QuestionEntity.class));
        verify(surveyRepository, times(1)).save(any(SurveyEntity.class));
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 추가")
    public void addSurvey_Success() {
        // given
        Integer surveyId = 1;
        String surveyTitle = "설문지";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true);

        Survey.Request request = Survey.Request.builder().build();
        Survey.Response response = new Survey.Response();
        List<Question.Response> responses = new ArrayList<>();

        when(surveyMapper.toSurveyEntity(request)).thenReturn(mockitoSurvey);
        when(surveyRepository.save(any(SurveyEntity.class))).thenReturn(mockitoSurvey);
        when(surveyMapper.toSurveyResponse(mockitoSurvey, responses)).thenReturn(response);

        // when
        Survey.Response result = surveyService.add(request);

        // then
        assertThat(result).isNotNull();

        verify(surveyRepository, times(1)).save(any(SurveyEntity.class));
    }
    
}