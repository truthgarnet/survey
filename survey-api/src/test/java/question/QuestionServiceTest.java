package question;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.QuestionType;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.repository.QuestionRepository;
import org.kong.survey.service.QuestionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("정상적인 접근 - surveyId를 통한 질문지 조회")
    public void findBySurvey_Success() {
        // given
        Integer surveyId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        List<QuestionEntity> mockitoQuestions = List.of(
                new QuestionEntity(questionId1, question1, questionType1, 1, true, SurveyEntity.builder().surveyId(surveyId).build()),
                new QuestionEntity(questionId2, question2, questionType2, 2, false, SurveyEntity.builder().surveyId(surveyId).build())
        );

        when(questionRepository.findBySurvey_surveyId(surveyId)).thenReturn(mockitoQuestions);

        // when
        List<QuestionEntity> result = questionService.findBySurvey(surveyId);

        // then
        assertThat(mockitoQuestions).isNotNull();
        assertThat(result).isNotNull();
        assertThat(result.get(0).getQuestionId()).isNotNull();
        assertThat(result.get(1).getQuestionId()).isNotNull();

        verify(questionRepository, times(1)).findBySurvey_surveyId(surveyId);
    }

    @Test
    @DisplayName("정상적인 접근 - 모두 추가")
    public void addAll_Success() {
        // given
        Integer surveyId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        List<QuestionEntity> questionEntities = List.of(
                new QuestionEntity(questionId1, question1, questionType1, 1, true, SurveyEntity.builder().surveyId(surveyId).build()),
                new QuestionEntity(questionId2, question2, questionType2, 2, false, SurveyEntity.builder().surveyId(surveyId).build())
        );

        // when
        questionService.addAll(questionEntities);

        // then
        verify(questionRepository, times(1)).saveAll(questionEntities);
    }

    @Test
    @DisplayName("List 비어있을 경우 - 모두 수정")
    public void updateAll_ListEmpty() {
        // given
        List<QuestionEntity> emptyList = new ArrayList<>();

        // when
        List<QuestionEntity> result = questionService.updateAll(emptyList);

        // then
        assertTrue(result.isEmpty());
        verify(questionRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("정상적인 접근 - 모두 수정")
    public void updateAll_Success() {
        // given
        Integer surveyId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        List<QuestionEntity> savedQuestions = List.of(
                new QuestionEntity(questionId1, question1, questionType1, 1, true, SurveyEntity.builder().surveyId(surveyId).build()),
                new QuestionEntity(questionId2, question2, questionType2, 2, false, SurveyEntity.builder().surveyId(surveyId).build())
        );

        List<QuestionEntity> changeQuestions = List.of(
                new QuestionEntity(questionId1, question1, questionType1, 1, true, SurveyEntity.builder().surveyId(surveyId).build()),
                new QuestionEntity(questionId2, question2, questionType2, 2, false, SurveyEntity.builder().surveyId(surveyId).build())
        );

        when(questionRepository.saveAll(savedQuestions)).thenReturn(changeQuestions);

        // when
        List<QuestionEntity> result = questionService.updateAll(savedQuestions);

        // then
        assertEquals(changeQuestions, result);
        verify(questionRepository, times(1)).saveAll(savedQuestions);
    }

    @Test
    @DisplayName("정상적인 접근 - 삭제")
    public void delete_Success() {
        // given
        Integer surveyId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        List<QuestionEntity> mockitoQuestions = List.of(
                new QuestionEntity(questionId1, question1, questionType1, 1, true, SurveyEntity.builder().surveyId(surveyId).build()),
                new QuestionEntity(questionId2, question2, questionType2, 2, false, SurveyEntity.builder().surveyId(surveyId).build())
        );

        when(questionRepository.findBySurvey_surveyId(surveyId)).thenReturn(mockitoQuestions);

        // when
        boolean result = questionService.delete(surveyId);

        // then
        assertTrue(result);
        verify(questionRepository, times(1)).deleteAll(mockitoQuestions);
    }


}
