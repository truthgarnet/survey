package org.kong.survey.controller;

import lombok.RequiredArgsConstructor;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.dto.UserAnswerFindAll;
import org.kong.survey.facade.SurveyFacade;
import org.kong.survey.service.UserSurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/surveys")
@RequiredArgsConstructor
public class UserSurveyController {

    private final UserSurveyService userSurveyService;

    private final SurveyFacade surveyFacade;

    @GetMapping("")
    public ResponseEntity<ResponseCommon<Object>> getUserSurveyList(@RequestParam(value = "page") int page,
                                                                    @RequestParam(value = "size") int size,
                                                                    @PathVariable(value = "userId") int userId) {
        PageDto surveyList = userSurveyService.findListByUserSurveyId(page, size, userId);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 리스트 불러 오기 성공")
                .data(surveyList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{surveyId}/user")
    public ResponseEntity<ResponseCommon<Object>> getUserSurvey(@PathVariable(value = "surveyId") int surveyId,
                                                                @PathVariable(value = "userId") int userId) {
        UserAnswer.SurveyResponse survey = surveyFacade.findUserSurvey(surveyId, userId);

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
        UserAnswer.SurveyResponse survey = surveyFacade.addSurvey(surveyId, request);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 작성 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
