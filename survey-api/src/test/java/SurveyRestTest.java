import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.kong.SurveyApplication;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.Question;
import org.kong.survey.dto.QuestionType;
import org.kong.survey.dto.Survey;
import org.kong.survey.entity.SurveyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.kong.survey.repository.SurveyRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SurveyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyRestTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;


    @BeforeEach
    public void setUp() {
        String url = "http://localhost:" + this.port + "/api/surveys";

        List<Question.Request> questions = List.of();
        Question.Request question1 = new Question.Request();
        question1.setQuestionType(QuestionType.SUB);
        question1.setQuestion("당신의 키는?");
        question1.setOrder(1);

        Question.Request question2 = new Question.Request();
        question1.setQuestionType(QuestionType.SUB);
        question1.setQuestion("당신의 몸무게는?");
        question1.setOrder(2);

        questions.add(question1);
        questions.add(question2);

        Survey.Request request = new Survey.Request();
        request.setSurveyId(1);
        request.setSurveyTitle("건강검진");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(questions);

        ResponseEntity<ResponseCommon> response = testRestTemplate.postForEntity(url, request, ResponseCommon.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("설문지 추가 테스트")
    public void addSurvey() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문");
        request.setSurveyVersion("3V");
        request.setUsedYn(true);
        request.setQuestions(null);

        String url = "http://localhost:" + this.port + "/api/surveys";
        ResponseEntity<ResponseCommon> response = testRestTemplate.postForEntity(url, request, ResponseCommon.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("설문지 리스트 조회 테스트")
    public void getSurveyList() throws Exception {
        // Entity 추가 하기
        String savedUrl = "http://localhost:" + this.port + "/api/surveys";

        List<Question.Request> questions = List.of();
        Question.Request question1 = new Question.Request();
        question1.setQuestionType(QuestionType.SUB);
        question1.setQuestion("당신의 키는?");
        question1.setOrder(1);

        Question.Request question2 = new Question.Request();
        question1.setQuestionType(QuestionType.SUB);
        question1.setQuestion("당신의 몸무게는?");
        question1.setOrder(2);

        questions.add(question1);
        questions.add(question2);

        Survey.Request request = new Survey.Request();
        request.setSurveyId(1);
        request.setSurveyTitle("건강검진");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(questions);

        ResponseEntity<ResponseCommon> response = testRestTemplate.postForEntity(savedUrl, request, ResponseCommon.class);
        SurveyEntity surveyEntity1 = surveyRepository.findBySurveyId(1).orElseThrow(() -> new RuntimeException());
        SurveyEntity surveyEntity2 = surveyRepository.findBySurveyId(2).orElseThrow(() -> new RuntimeException());


        String url = "http://localhost:" + this.port + "/api/surveys";

        response = testRestTemplate.getForEntity(
                url,
                null,
                ResponseCommon.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    @DisplayName("설문지 단건 조회 테스트")
    public void getSurvey() throws Exception {
        // given
        int surveyId = 1;


    }



    @Test
    @DisplayName("설문지 수정 테스트")
    public void updateAll() throws Exception {
        // given
        Integer surveyId = 1;

        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("수정된설문");
        request.setSurveyVersion("2V");
        request.setUsedYn(false);
        request.setQuestions(null);

    }

    @Test
    @DisplayName("설문지 삭제 테스트")
    public void deleteSurvey() throws Exception {
        // given
        Integer surveyId = 1;

    }
}
