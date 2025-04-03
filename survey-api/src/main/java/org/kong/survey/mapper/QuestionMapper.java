package org.kong.survey.mapper;

import org.kong.survey.dto.Question;
import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "questionRequests.questionId", target = "questionId")
    @Mapping(source = "questionRequests.question", target = "question")
    @Mapping(source = "questionRequests.questionType", target = "questionType")
    @Mapping(source = "questionRequests.order", target = "questionOrder")
    @Mapping(target = "survey", expression = "java(surveyEntity)")
    QuestionEntity toQuestionEntity(Question.Request questionRequests, SurveyEntity surveyEntity);

    default List<QuestionEntity> toQuestionEntityList(SurveyEntity surveyEntity, List<Question.Request> questionRequests) {
        if (questionRequests == null) {
            return new ArrayList<>();
        }
        return questionRequests.stream()
                .map(request -> toQuestionEntity(request, surveyEntity))
                .collect(Collectors.toList());
    }

    @Mapping(source = "questionEntity.questionOrder", target = "order")
    @Mapping(source = "surveyAnswerResponses", target = "answers")
    Question.Response toQuestionResponse(QuestionEntity questionEntity, List<SurveyAnswer.Response> surveyAnswerResponses);

    @Mapping(source = "questionEntity.questionOrder", target = "order")
    @Mapping(target = "answers", ignore = true) // `answers` 필드 제외)
    List<Question.Response> toQuestionResponseList(List<QuestionEntity> questionEntity);
}
