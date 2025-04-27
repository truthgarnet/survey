package org.kong.survey.controller;

import lombok.RequiredArgsConstructor;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.PageDto;
import org.kong.survey.dto.UserAnswer;
import org.kong.survey.facade.UserSurveyFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/surveys")
@RequiredArgsConstructor
public class UserSurveyController {

    private final UserSurveyFacade userSurveyFacade;


    @GetMapping("")
    public ResponseEntity<ResponseCommon<Object>> getUserSurveyList(@RequestParam(value = "page") int page,
                                                                    @RequestParam(value = "size") int size) {
        PageDto userSurveyList = userSurveyFacade.findListByUserSurveyId(page, size);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 리스트 불러 오기 성공")
                .data(userSurveyList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{surveyId}/user")
    public ResponseEntity<ResponseCommon<Object>> getUserSurvey(@PathVariable(value = "surveyId") int surveyId,
                                                                @PathVariable(value = "userId") int userId) {
        UserAnswer.SurveyResponse survey = userSurveyFacade.findUserSurvey(surveyId, userId);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("사용자 설문지 불러 오기 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{surveyId}/user")
    public ResponseEntity<ResponseCommon<Object>> writeSurvey(@RequestBody List<UserAnswer.Request> request) {
        UserAnswer.SurveyResponse survey = userSurveyFacade.addSurvey(request);

        ResponseCommon<Object> response = ResponseCommon
                .builder()
                .code(1)
                .msg("설문지 작성 성공")
                .data(survey)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
