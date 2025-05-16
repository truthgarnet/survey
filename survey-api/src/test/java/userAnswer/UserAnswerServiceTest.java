package userAnswer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.QuestionType;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.kong.survey.repository.UserAnswerRepository;
import org.kong.survey.service.UserAnswerService;
import org.kong.user.entity.UserEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAnswerServiceTest {

    @Mock
    private UserAnswerRepository userAnswerRepository;

    @InjectMocks
    private UserAnswerService userAnswerService;

    @Test
    @DisplayName("정상적인 접근 - 사용자 정답 리스트 조회")
    public void findUserAnswers_Success() {
        // given
        Integer surveyId = 1;
        Integer userId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        Integer userAnswerId1 = 1;
        String userAnswer1 = "56";
        LocalDateTime now = LocalDateTime.now();

        Integer userAnswerId2 = 2;
        String userAnswer2 = "168";

        UserEntity userEntity = new UserEntity("테스트", "테스트", "USER");
        QuestionEntity questionEntity1 = new QuestionEntity(questionId1, question1, questionType1, 1, true,
                SurveyEntity.builder().surveyId(surveyId).build());
        QuestionEntity questionEntity2 = new QuestionEntity(questionId2, question2, questionType2, 2, false,
                SurveyEntity.builder().surveyId(surveyId).build());
        List<QuestionEntity> questionEntityList = List.of(questionEntity1, questionEntity2);

        List<UserAnswerEntity> userAnswerEntity = List.of(
                new UserAnswerEntity(userAnswerId1, 1, userAnswer1, now, userEntity, questionEntity1),
                new UserAnswerEntity(userAnswerId2, 3, userAnswer2, now, userEntity, questionEntity2));

        when(userAnswerRepository.findByQuestionAndUser_UserId(questionEntityList, userId)).thenReturn(userAnswerEntity);

        // when
        List<UserAnswerEntity> result = userAnswerService.findUserAnswers(questionEntityList, userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUserAnswerId()).isNotNull();
        assertThat(result.get(1).getUserAnswerId()).isNotNull();

        verify(userAnswerRepository, times(1)).findByQuestionAndUser_UserId(questionEntityList, userId);
    }

    @Test
    @DisplayName("정상적인 접근 - 사용자 정답 등록")
    public void addSurvey_Success() {
        // given
        Integer surveyId = 1;
        Integer userId = 1;
        Integer questionId1 = 1;
        String question1 = "당신의 몸무게는 몇입니까?";
        QuestionType questionType1 = QuestionType.SUB;

        Integer questionId2 = 2;
        String question2 = "당신의 키는 몇입니까?";
        QuestionType questionType2 = QuestionType.SUB;

        LocalDateTime now = LocalDateTime.now();

        UserEntity userEntity = new UserEntity("테스트", "테스트", "USER");
        QuestionEntity questionEntity1 = new QuestionEntity(questionId1, question1, questionType1, 1, true,
                SurveyEntity.builder().surveyId(surveyId).build());
        QuestionEntity questionEntity2 = new QuestionEntity(questionId2, question2, questionType2, 2, false,
                SurveyEntity.builder().surveyId(surveyId).build());

        List<UserAnswerEntity> userAnswerEntities = List.of(
                new UserAnswerEntity(1, 1, "답변1", now, userEntity, questionEntity1),
                new UserAnswerEntity(2, 3, "답변2", now, userEntity, questionEntity2));

        when(userAnswerRepository.saveAll(userAnswerEntities)).thenReturn(userAnswerEntities);

        // when
        List<UserAnswerEntity> result = userAnswerService.addSurvey(userAnswerEntities);

        // then
        assertEquals(userAnswerEntities, result);
        verify(userAnswerRepository, times(1)).saveAll(userAnswerEntities);
    }
}
