package org.kong.survey.mapper;

import org.kong.survey.dto.Question;
import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public List<QuestionEntity> toQuestionEntityList(SurveyEntity surveyEntity,
                                                     List<Question.Request> questionRequestList) {
        if (questionRequestList == null || questionRequestList.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        return questionRequestList.stream()
                .map(request -> QuestionEntity.builder()
                        .question(request.getQuestion()) // 질문 내용 매핑
                        .questionOrder(request.getOrder()) // 질문 순서 매핑
                        .survey(surveyEntity) // SurveyEntity 매핑
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Question.Response> toQuestionResponseList(List<QuestionEntity> questionEntityList) {
        if (questionEntityList == null || questionEntityList.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        return questionEntityList.stream()
                .map(response -> Question.Response.builder()
                        .question(response.getQuestion())
                        .questionType(response.getQuestionType())
                        .build())
                .collect(Collectors.toList());
    }

}
