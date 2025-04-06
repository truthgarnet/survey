package integration;

import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.kong.SurveyApplication;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyRestTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("설문지 추가 테스트")
    public void addSurvey() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문지");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(null);

        String url = "/api/surveys";
        ResponseEntity<ResponseCommon<Survey.Response>> response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Survey.Response survey = response.getBody().getData();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(survey.getSurveyId()).isNotNull();
        assertThat(survey.getSurveyTitle()).isEqualTo("새로운설문지");
        assertThat(survey.getSurveyVersion()).isEqualTo("1V");
    }

    @Test
    @DisplayName("설문지 리스트 조회 테스트")
    public void getSurveyList() throws Exception {
        // 1. 데이터 저장
        Survey.Request request1 = new Survey.Request();
        request1.setSurveyTitle("건강검진");
        request1.setSurveyVersion("1V");
        request1.setUsedYn(true);
        request1.setQuestions(null);

        String url = "/api/surveys";
        testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request1), new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Survey.Request request2 = new Survey.Request();
        request2.setSurveyTitle("건강검진");
        request2.setSurveyVersion("1V");
        request2.setUsedYn(true);
        request2.setQuestions(null);

        url = "/api/surveys";

        testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request2), new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        int page = 0;
        int size = 10;

        url = "/api/surveys?page=" + page + "&size=" + size;

        ResponseEntity<ResponseCommon<PageDto<SurveyFindAll.Response>>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseCommon<PageDto<SurveyFindAll.Response>>>(){}
        );

        PageDto<SurveyFindAll.Response> survey = response.getBody().getData();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(survey.getContent().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("설문지 단건 조회 테스트")
    public void getSurvey() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문지");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(null);

        String url = "/api/surveys";
        testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );


        Integer surveyId = 1;
        url = "/api/surveys/" + surveyId;

        ResponseEntity<ResponseCommon<Survey.Response>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Survey.Response survey = response.getBody().getData();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(survey.getSurveyId()).isNotNull();
        assertThat(survey.getSurveyTitle()).isEqualTo("새로운설문지");
        assertThat(survey.getSurveyVersion()).isEqualTo("1V");
    }



    @Test
    @DisplayName("설문지 수정 테스트")
    public void updateAll() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문지");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(null);

        String url = "/api/surveys";
        ResponseEntity<ResponseCommon<Survey.Response>> response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Integer surveyId = 1;

        request = new Survey.Request();
        request.setSurveyId(surveyId);
        request.setSurveyTitle("수정된설문");
        request.setSurveyVersion("2V");
        request.setUsedYn(false);
        request.setQuestions(null);

        url = "/api/surveys/" + surveyId;
        response = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Survey.Response survey = response.getBody().getData();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(survey.getSurveyId()).isNotNull();
        assertThat(survey.getSurveyTitle()).isEqualTo("수정된설문");
        assertThat(survey.getSurveyVersion()).isEqualTo("2V");

    }

    @Test
    @DisplayName("설문지 삭제 테스트")
    public void deleteSurvey() throws Exception {
        // given
        Survey.Request request = new Survey.Request();
        request.setSurveyTitle("새로운설문지");
        request.setSurveyVersion("1V");
        request.setUsedYn(true);
        request.setQuestions(null);

        String url = "/api/surveys";
        testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<ResponseCommon<Survey.Response>>(){}
        );

        Integer surveyId = 1;

        url = "/api/surveys/" + surveyId;
        ResponseEntity<HashMap> response = testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                HashMap.class
        );

        HashMap<String, Object> result = response.getBody();
        HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");

        int value = (int) data.get("result");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(value).isEqualTo(1);
    }
}