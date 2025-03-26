import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kong.SurveyApplication;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.repository.SurveyRepository;
import org.kong.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = SurveyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
public class SurveyTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SurveyService surveyService;

    @Autowired
    private SurveyRepository surveyRepository;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터 준비
        SurveyEntity survey1 = new SurveyEntity(1, "건강설문", "1V", LocalDateTime.now(), LocalDateTime.now(), true);
        SurveyEntity survey2 = new SurveyEntity(2, "MBTI설문", "2V", LocalDateTime.now().minusHours(1) , LocalDateTime.now(), true);

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);
    }

    @Test
    @DisplayName("설문지 리스트 조회 테스트")
    public void getSurveyList() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourBefore = LocalDateTime.now().minusHours(1);

        mockMvc.perform(get("/api/surveys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1)) // 응답 코드
                .andExpect(jsonPath("$.msg").value("설문지 리스트 불러 오기 성공")) // 메세지 확인
                .andExpect(jsonPath("$.data.size()").value(2))
                .andExpect(jsonPath("$.data[0].surveyId").value(1))
                .andExpect(jsonPath("$.data[0].surveyTitle").value("건강설문"))
                .andExpect(jsonPath("$.data[0].surveyVersion").value("1V"))
                .andExpect(jsonPath("$.data[0].createdDate").value(now))
                .andExpect(jsonPath("$.data[1].surveyId").value(2))
                .andExpect(jsonPath("$.data[1].surveyTitle").value("MBTI설문"))
                .andExpect(jsonPath("$.data[1].surveyVersion").value("2V"))
                .andExpect(jsonPath("$.data[1].createdDate").value(oneHourBefore));
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
                .andExpect(jsonPath("$.data.surveyVersion").value("1V"));  // surveyVersion 확인
    }

    @Test
    @DisplayName("설문지 추가 테스트")
    public void addSurvey() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문");
        request.setUsedYn(true);
        request.setQuestions(null);

        // SurveyService가 add 호출 시 Response 객체를 반환하도록 Mock 설정
        Survey.Response surveyResponse = null;

        surveyResponse = Survey.Response.builder()
                        .surveyId(3)
                        .surveyTitle(request.getSurveyTitle())
                        .surveyVersion("3V")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .questions(null)
                        .build();

        given(surveyService.add(request)).willReturn(surveyResponse);


        // when & then
        mockMvc.perform(post("/api/surveys")
                .content("{\"surveyTitle\":\"새로운설문\", \"surveyVersion\":\"3V\", \"startDate\":\"" + LocalDateTime.now() + "\", \"endDate\":\"" + LocalDateTime.now() + "\", \"isUsedYn\":true}"))
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
        Integer surveyId = 3;

        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("수정된설문");
        request.setUsedYn(true);
        request.setQuestions(null);

        // SurveyService가 add 호출 시 Response 객체를 반환하도록 Mock 설정
        Survey.Response surveyResponse = null;

        surveyResponse = Survey.Response.builder()
                        .surveyId(3)
                        .surveyTitle(request.getSurveyTitle())
                        .surveyVersion("3V")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .questions(null)
                        .build();

        given(surveyService.add(request)).willReturn(surveyResponse);


        // when & then
        mockMvc.perform(post("/api/surveys/{surveyId}", surveyId)
                .content("{\"surveyTitle\":\"수정된설문\", \"surveyVersion\":\"3V\", \"startDate\":\"" + LocalDateTime.now() + "\", \"endDate\":\"" + LocalDateTime.now() + "\", \"isUsedYn\":true}"))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 전체 수정 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.surveyId").value(3))  // 설문지 ID 확인
                .andExpect(jsonPath("$.data.surveyTitle").value("수정된설문"))  // 설문지 제목 확인
                .andExpect(jsonPath("$.data.surveyVersion").value("3V"));  // 설문지 버전 확인
    }

    @Test
    @DisplayName("설문지 삭제 테스트")
    public void deleteSurvey() throws Exception {
        // given
        Integer surveyId = 1;

        given(surveyService.delete(surveyId)).willReturn(1); // 삭제 성공 시 true 반환

        // when & then
        mockMvc.perform(delete("/api/surveys/{surveyId}", surveyId))
                .andExpect(status().isOk())  // HTTP 상태 코드 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // 응답 타입 확인
                .andExpect(jsonPath("$.code").value(1))  // 응답 코드 확인
                .andExpect(jsonPath("$.msg").value("설문지 삭제 성공"))  // 응답 메시지 확인
                .andExpect(jsonPath("$.data.result").value(1));  // 삭제 결과 확인
    }
}
