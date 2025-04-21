package org.kong.survey.mapper;

import org.kong.survey.dto.UserAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;
import org.kong.survey.entity.UserAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserAnswerMapper {

    UserAnswerMapper INSTANCE = Mappers.getMapper(UserAnswerMapper.class);

    @Mapping(source = "question.questionId", target = "questionId")
    @Mapping(target = "answerId", ignore = true) // answer가 없으니 무시
    @Mapping(source = "question.question", target = "question")
    @Mapping(source = "userAnswer", target = "answer")
    @Mapping(source = "answerDate", target = "answerDate")
    UserAnswer.Response toResponse(UserAnswerEntity entity);

    default UserAnswer.SurveyResponse toUserAnswerResponse(List<UserAnswerEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new UserAnswer.SurveyResponse(null, null, Collections.emptyList());
        }

        // Survey 정보 추출
        QuestionEntity firstQuestion = entities.get(0).getQuestion();
        SurveyEntity survey = firstQuestion.getSurvey();

        Integer surveyId = survey.getSurveyId();
        String surveyTitle = survey.getSurveyTitle();

        List<UserAnswer.Response> responseList = entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new UserAnswer.SurveyResponse(surveyId, surveyTitle, responseList);
    }

    UserAnswer.SurveyResponse toUserSurveyResponse(SurveyEntity surveyEntity, List<QuestionEntity> questionEntities, List<UserAnswerEntity> userAnswerEntities);

    List<UserAnswerEntity> toUserAnswerEntity(List<UserAnswer.Request> request);
}
