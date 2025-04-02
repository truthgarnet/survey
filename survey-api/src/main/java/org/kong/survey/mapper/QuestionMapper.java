package org.kong.survey.mapper;

import org.kong.survey.dto.Question;
import org.kong.survey.dto.Survey;
import org.kong.survey.dto.SurveyAnswer;
import org.kong.survey.entity.QuestionEntity;
import org.kong.survey.entity.SurveyEntity;

import java.util.List;


public interface QuestionMapper {

    List<QuestionEntity> toQuestionEntityList(SurveyEntity surveyEntity,
                                                     List<Question.Request> questionRequests);

    List<Question.Response> toQuestionResponseList(List<QuestionEntity> questionEntityList);
}
