package org.kong.survey.controller;

import lombok.RequiredArgsConstructor;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyFindAll;
import org.kong.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("")
    public ResponseEntity<ResponseCommon<Object>> getSurveyList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        PageDto<SurveyFindAll.Response> surveyList = surveyService.findAll(page, size);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 리스트 불러 오기 성공")
                .data(surveyList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<ResponseCommon<Object>> getSurvey(@PathVariable(value = "surveyId") int surveyId) {
        Survey.Response survey = surveyService.findBySurveyId(surveyId);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 단건 불러 오기 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseCommon<Object>> addSurvey(@RequestBody Survey.Request request) {
        Survey.Response survey = surveyService.add(request);

        ResponseCommon<Object> response = ResponseCommon.builder()
                .code(1)
                .msg("설문지 추가 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<ResponseCommon<Object>> updateAll(@PathVariable(value = "surveyId") int surveyId,
                                                            @RequestBody Survey.Request request) {
        Survey.Response survey = surveyService.updateAll(surveyId, request);

        ResponseCommon<Object> response = ResponseCommon.builder()
                .code(1)
                .msg("설문지 전체 수정 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{surveyId}")
    public ResponseEntity<ResponseCommon<Object>> updatePart(@PathVariable(value = "surveyId") int surveyId, @RequestBody Survey.Request request) {
        Survey.Response survey = surveyService.updatePart(surveyId, request);

        ResponseCommon<Object> response = ResponseCommon.builder()
                .code(1)
                .msg("설문지 일부 수정 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<ResponseCommon<Object>> deleteSurvey(@PathVariable(value = "surveyId") int surveyId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("result", surveyService.delete(surveyId));

        ResponseCommon<Object> response = ResponseCommon.builder()
                .code(1)
                .msg("설문지 삭제 성공")
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
