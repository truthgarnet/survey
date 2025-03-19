package org.kong.survey.controller;

import org.kong.response.ResponseCommon;
import org.kong.survey.dto.Survey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/menu/user/survey")
public class UserSurveyController {
    @GetMapping("/list")
    public ResponseEntity<ResponseCommon<Object>> getUserSurveyList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        List<Survey.Response> surveyList = new ArrayList<>();

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 리스트 불러 오기 성공")
                .data(surveyList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{surveyId}/user/{userId}")
    public ResponseEntity<ResponseCommon<Object>> getUserSurvey(@PathVariable(value = "surveyId") int surveyId,
                                                                @PathVariable(value = "userId") int userId) {
        Survey.Response survey = new Survey.Response();

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 단건 불러 오기 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
