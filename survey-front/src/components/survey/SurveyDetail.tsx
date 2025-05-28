import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useParams, useNavigate } from 'react-router-dom';
import { surveyApi } from '../../services/api';
import type { Survey } from '../../types/survey';

export default function SurveyDetail() {
  const { surveyId } = useParams<{ surveyId: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: survey, isLoading, error } = useQuery({
    queryKey: ['survey', surveyId],
    queryFn: async () => {
      const response = await surveyApi.getSurvey(Number(surveyId));
      return response.data;
    },
  });

  const deleteMutation = useMutation({
    mutationFn: () => surveyApi.deleteSurvey(Number(surveyId)),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['surveys'] });
      navigate('/surveys');
    },
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error loading survey</div>;
  }

  if (!survey) {
    return <div>Survey not found</div>;
  }

  return (
    <div className="bg-white shadow overflow-hidden sm:rounded-lg">
      <div className="px-4 py-5 sm:px-6">
        <div className="flex justify-between items-center">
          <div>
            <h3 className="text-lg leading-6 font-medium text-gray-900">
              {survey.surveyTitle}
            </h3>
            <p className="mt-1 max-w-2xl text-sm text-gray-500">
              버전: {survey.surveyVersion}
            </p>
          </div>
          <div className="flex space-x-4">
            <button
              onClick={() => navigate(`/surveys/${surveyId}/edit`)}
              className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              수정
            </button>
            <button
              onClick={() => deleteMutation.mutate()}
              className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
            >
              삭제
            </button>
          </div>
        </div>
      </div>
      <div className="border-t border-gray-200">
        <dl>
          <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt className="text-sm font-medium text-gray-500">생성일</dt>
            <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              {new Date(survey.createdDate).toLocaleDateString()}
            </dd>
          </div>
          <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt className="text-sm font-medium text-gray-500">수정일</dt>
            <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              {new Date(survey.updatedDate).toLocaleDateString()}
            </dd>
          </div>
          <div className="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt className="text-sm font-medium text-gray-500">사용 여부</dt>
            <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              {survey.usedYn ? '사용 중' : '미사용'}
            </dd>
          </div>
        </dl>
      </div>
      <div className="px-4 py-5 sm:px-6">
        <h4 className="text-lg font-medium text-gray-900">문항 목록</h4>
        <div className="mt-4 space-y-4">
          {survey.questions.map((question, index) => (
            <div
              key={question.questionId}
              className="bg-white shadow overflow-hidden sm:rounded-lg"
            >
              <div className="px-4 py-5 sm:px-6">
                <h5 className="text-md font-medium text-gray-900">
                  {index + 1}. {question.question}
                </h5>
                <p className="mt-1 text-sm text-gray-500">
                  유형: {question.questionType}
                </p>
              </div>
              <div className="border-t border-gray-200 px-4 py-5 sm:px-6">
                <div className="space-y-2">
                  {question.answers.map((answer) => (
                    <div
                      key={answer.answerId}
                      className="flex items-center space-x-2"
                    >
                      <input
                        type={
                          question.questionType === 'MULTIPLE'
                            ? 'checkbox'
                            : 'radio'
                        }
                        name={`question-${question.questionId}`}
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      />
                      <label className="text-sm text-gray-700">
                        {answer.answer}
                      </label>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
} 