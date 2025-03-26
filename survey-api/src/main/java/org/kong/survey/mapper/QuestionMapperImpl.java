package org.kong.survey.mapper;

import org.kong.survey.dto.Question;
import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

}
