import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { surveyApi } from '../../services/api';
import type { Survey, PageDto } from '../../types/survey';

export default function SurveyList() {
  const [page, setPage] = useState(1);
  const [search, setSearch] = useState('');
  const size = 10;

  const { data, isLoading, error } = useQuery({
    queryKey: ['surveys', page, size, search],
    queryFn: async () => {
      const response = await surveyApi.getSurveyList(page, size, search);
      return response.data;
    },
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error loading surveys</div>;
  }

  return (
    <div className="bg-white shadow overflow-hidden sm:rounded-md">
      <div className="px-4 py-5 sm:px-6">
        <div className="flex justify-between items-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">설문 목록</h3>
          <div className="flex space-x-4">
            <input
              type="text"
              placeholder="검색어를 입력하세요"
              className="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <Link
              to="/surveys/new"
              className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              새 설문 만들기
            </Link>
          </div>
        </div>
      </div>
      <ul className="divide-y divide-gray-200">
        {data?.content.map((survey: Survey) => (
          <li key={survey.surveyId}>
            <Link
              to={`/surveys/${survey.surveyId}`}
              className="block hover:bg-gray-50"
            >
              <div className="px-4 py-4 sm:px-6">
                <div className="flex items-center justify-between">
                  <p className="text-sm font-medium text-indigo-600 truncate">
                    {survey.surveyTitle}
                  </p>
                  <div className="ml-2 flex-shrink-0 flex">
                    <p className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                      {survey.surveyVersion}
                    </p>
                  </div>
                </div>
                <div className="mt-2 sm:flex sm:justify-between">
                  <div className="sm:flex">
                    <p className="flex items-center text-sm text-gray-500">
                      생성일: {new Date(survey.createdDate).toLocaleDateString()}
                    </p>
                  </div>
                  <div className="mt-2 flex items-center text-sm text-gray-500 sm:mt-0">
                    <p>
                      수정일: {new Date(survey.updatedDate).toLocaleDateString()}
                    </p>
                  </div>
                </div>
              </div>
            </Link>
          </li>
        ))}
      </ul>
      <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
        <div className="flex-1 flex justify-between sm:hidden">
          <button
            onClick={() => setPage(page - 1)}
            disabled={page === 1}
            className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
          >
            이전
          </button>
          <button
            onClick={() => setPage(page + 1)}
            disabled={!data || page >= data.totalPages}
            className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
          >
            다음
          </button>
        </div>
        <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
          <div>
            <p className="text-sm text-gray-700">
              총 <span className="font-medium">{data?.totalElements}</span> 개의 설문
            </p>
          </div>
          <div>
            <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px">
              <button
                onClick={() => setPage(page - 1)}
                disabled={page === 1}
                className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
              >
                이전
              </button>
              <button
                onClick={() => setPage(page + 1)}
                disabled={!data || page >= data.totalPages}
                className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
              >
                다음
              </button>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
} 