package org.kong.survey.controller;

import org.kong.response.ResponseCommon;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.dto.UserAnswerFindAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/surveys")
public class UserSurveyController {
    @GetMapping("")
    public ResponseEntity<ResponseCommon<Object>> getUserSurveyList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        List<UserAnswerFindAll.Response> surveyList = new ArrayList<>();

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 리스트 불러 오기 성공")
                .data(surveyList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{surveyId}/user")
    public ResponseEntity<ResponseCommon<Object>> getUserSurvey(@PathVariable(value = "surveyId") int surveyId) {
        UserAnswer.Response survey = new UserAnswer.Response();

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("사용자 설문지 불러 오기 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{surveyId}/user")
    public ResponseEntity<ResponseCommon<Object>> writeSurvey(@PathVariable(value = "surveyId") int surveyId,
                                                              @RequestBody List<UserAnswer.Request> request) {
        UserAnswer.Response survey = new UserAnswer.Response();

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 작성 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
