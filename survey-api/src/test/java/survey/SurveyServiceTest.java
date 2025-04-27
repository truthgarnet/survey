package survey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.SurveyStatus;
import org.kong.survey.mapper.SurveyMapper;
import org.kong.survey.repository.SurveyRepository;
import org.kong.survey.service.SurveyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @InjectMocks
    private SurveyService surveyService;

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 단건 조회")
    public void getSurvey_Exception() {
        // given
        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> surveyService.findBySurveyId(1));

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
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true,
                SurveyStatus.PUBLISHED);

        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.of(mockitoSurvey));

        // when
        SurveyEntity result = surveyService.findBySurveyId(1);

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
        SurveyEntity mockitoSurvey1 = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true,
                SurveyStatus.PUBLISHED);
        surveyEntityList.add(mockitoSurvey1);

        Integer surveyId2 = 2;
        String surveyTitle2 = "설문지2";
        String surveyVersion2 = "2V";
        SurveyEntity mockitoSurvey2 = new SurveyEntity(surveyId2, surveyTitle2, surveyVersion2, true,
                SurveyStatus.PUBLISHED);
        surveyEntityList.add(mockitoSurvey2);

        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SurveyEntity> surveyPages = new PageImpl<>(surveyEntityList, pageRequest, surveyEntityList.size());
        when(surveyRepository.findAll(pageRequest)).thenReturn(surveyPages);

        // when
        Page<SurveyEntity> result = surveyService.findAll(page, size);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        verify(surveyRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 삭제")
    public void delete_Exception() {
        // given
        Exception exception = assertThrows(RuntimeException.class, () -> surveyService.delete(1));

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
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true,
                SurveyStatus.DELETED);

        when(surveyRepository.findBySurveyId(1)).thenReturn(Optional.of(mockitoSurvey));
        doNothing().when(surveyRepository).updateSurveyStatus(surveyId, SurveyStatus.DELETED);

        // when
        boolean result = surveyService.delete(surveyId);

        // then
        assertThat(result).isEqualTo(true);

        verify(surveyRepository, times(1)).updateSurveyStatus(surveyId, SurveyStatus.DELETED);
    }

    @Test
    @DisplayName("존재하지 않은 설문지에 접근 시 예외 발생 - 설문지 수정")
    public void updateAll_Exception() {
        // given
        Survey.Request request = Survey.Request.builder().build();

        Exception exception = assertThrows(RuntimeException.class, () -> surveyService.updateAll(1, request));

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
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true,
                SurveyStatus.PUBLISHED);
        SurveyEntity updatedSurveyEntity = new SurveyEntity(2, "수정된설문지", "2V", false,
                SurveyStatus.PUBLISHED);

        Survey.Request request = Survey.Request.builder()
                .surveyId(2)
                .surveyVersion("2V")
                .surveyTitle("수정된설문지")
                .build();

        when(surveyRepository.findBySurveyId(surveyId)).thenReturn(Optional.of(mockitoSurvey));
        when(surveyMapper.toSurveyEntityUpdate(surveyId, request)).thenReturn(updatedSurveyEntity);

        // when
        SurveyEntity result = surveyService.updateAll(surveyId, request);

        // then
        assertThat(result).isNotNull();

        verify(surveyRepository, times(1)).save(any(SurveyEntity.class));
    }

    @Test
    @DisplayName("정상적인 접근 - 설문지 추가")
    public void addSurvey_Success() {
        // given
        Integer surveyId = 1;
        String surveyTitle = "설문지";
        String surveyVersion = "1V";
        SurveyEntity mockitoSurvey = new SurveyEntity(surveyId, surveyTitle, surveyVersion, true,
                SurveyStatus.PUBLISHED);

        Survey.Request request = Survey.Request.builder().build();

        when(surveyMapper.toSurveyEntity(request)).thenReturn(mockitoSurvey);
        when(surveyRepository.save(any(SurveyEntity.class))).thenReturn(mockitoSurvey);

        // when
        SurveyEntity result = surveyService.add(request);

        // then
        assertThat(result).isNotNull();

        verify(surveyRepository, times(1)).save(any(SurveyEntity.class));
    }

}