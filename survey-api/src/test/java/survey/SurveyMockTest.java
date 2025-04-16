package survey;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kong.SurveyApplication;
import org.kong.config.JpaConfig;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.repository.QuestionRepository;
import org.kong.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SurveyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
@Import(JpaConfig.class)
public class SurveyMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // 외래 키 제약 조건 비활성화
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE TB_SURVEY RESTART IDENTITY").executeUpdate();
        // 외래 키 제약 조건 다시 활성화
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

        SurveyEntity survey1 = SurveyEntity.builder()
                .surveyTitle("건강설문")
                .surveyVersion("1V")
                .usedYn(true)
                .build();

        SurveyEntity survey2 = SurveyEntity.builder()
                .surveyTitle("MBTI설문")
                .surveyVersion("2V")
                .usedYn(true)
                .build();

        surveyRepository.saveAndFlush(survey1);
        surveyRepository.saveAndFlush(survey2);

        QuestionEntity question1 = QuestionEntity.builder()
                .question("당신의 몸무게는?")
                .questionOrder(1)
                .survey(survey1)
                .build();

        QuestionEntity question2 = QuestionEntity.builder()
                .question("당신의 키는?")
                .questionOrder(2)
                .survey(survey1)
                .build();

        questionRepository.saveAndFlush(question1);
        questionRepository.saveAndFlush(question2);

    }

    @Test
    @DisplayName("설문지 리스트 조회 테스트")
    public void getSurveyList() throws Exception {

        mockMvc.perform(get("/api/surveys?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1)) // 응답 코드
                .andExpect(jsonPath("$.msg").value("설문지 리스트 불러 오기 성공")) // 메세지 확인
                .andExpect(jsonPath("$.data.content.size()").value(2))
                .andExpect(jsonPath("$.data.content[0].surveyId").value(1))
                .andExpect(jsonPath("$.data.content[0].surveyTitle").value("건강설문"))
                .andExpect(jsonPath("$.data.content[0].surveyVersion").value("1V"))
                .andExpect(jsonPath("$.data.content[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.data.content[0].updatedDate").isNotEmpty())
                .andExpect(jsonPath("$.data.content[1].surveyId").value(2))
                .andExpect(jsonPath("$.data.content[1].surveyTitle").value("MBTI설문"))
                .andExpect(jsonPath("$.data.content[1].surveyVersion").value("2V"))
                .andExpect(jsonPath("$.data.content[1].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.data.content[1].updatedDate").isNotEmpty());
    }

    @Test
    @DisplayName("설문지 단건 조회 테스트")
    public void getSurvey() throws Exception {
        // given
        int surveyId = 1;

        // then
        mockMvc.perform(get("/api/surveys/{surveyId}", surveyId))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 단건 불러 오기 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.surveyId").value(surveyId))  // surveyId 값 확인
                .andExpect(jsonPath("$.data.surveyTitle").value("건강설문"))  // surveyTitle 확인
                .andExpect(jsonPath("$.data.surveyVersion").value("1V"))
                .andExpect(jsonPath("$.data.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.data.updatedDate").isNotEmpty())
                .andExpect(jsonPath("$.data.usedYn").value(true));
    }

    @Test
    @DisplayName("설문지 추가 테스트")
    public void addSurvey() throws Exception {
        // given
        Survey.Request request = Survey.Request.builder()
                .surveyTitle("새로운설문")
                .surveyVersion("3V")
                .usedYn(true)
                .questions(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        // when & then
        mockMvc.perform(post("/api/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 추가 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.surveyId").value(3))  // 설문지 ID 확인
                .andExpect(jsonPath("$.data.surveyTitle").value("새로운설문"))  // 설문지 제목 확인
                .andExpect(jsonPath("$.data.surveyVersion").value("3V"));  // 설문지 버전 확인
    }

    @Test
    @DisplayName("설문지 수정 테스트")
    public void updateAll() throws Exception {
        // given
        Integer surveyId = 1;

        Survey.Request request = Survey.Request.builder()
                .surveyTitle("수정된설문")
                .surveyVersion("2V")
                .usedYn(false)
                .questions(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        // when & then
        mockMvc.perform(put("/api/surveys/{surveyId}", surveyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 전체 수정 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.surveyId").value(1))  // 설문지 ID 확인
                .andExpect(jsonPath("$.data.surveyTitle").value("수정된설문"))  // 설문지 제목 확인
                .andExpect(jsonPath("$.data.surveyVersion").value("2V"))
                .andExpect(jsonPath("$.data.usedYn").value(false));  // 설문지 버전 확인
    }

    @Test
    @DisplayName("설문지 삭제 테스트")
    public void deleteSurvey() throws Exception {
        // given
        Integer surveyId = 1;

        // when & then
        mockMvc.perform(delete("/api/surveys/{surveyId}", surveyId))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 삭제 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.result").value(1));  // 삭제 결과 확인
    }
}
