import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { userApi } from '../../services/api';
import type { UserRequest } from '../../types/user';

export default function LoginForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<UserRequest>({
    userId: 0,
    userPwd: '',
  });
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await userApi.login(formData);
      navigate('/surveys');
    } catch (err) {
      setError('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'userId' ? parseInt(value) || 0 : value,
    }));
  };

  return (
    <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            로그인
          </h2>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="rounded-md shadow-sm -space-y-px">
            <div>
              <label htmlFor="userId" className="sr-only">
                아이디
              </label>
              <input
                id="userId"
                name="userId"
                type="number"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="아이디"
                value={formData.userId || ''}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="userPwd" className="sr-only">
                비밀번호
              </label>
              <input
                id="userPwd"
                name="userPwd"
                type="password"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                placeholder="비밀번호"
                value={formData.userPwd}
                onChange={handleChange}
              />
            </div>
          </div>

          {error && (
            <div className="text-red-500 text-sm text-center">{error}</div>
          )}

          <div>
            <button
              type="submit"
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              로그인
            </button>
          </div>
        </form>
        <div className="text-center">
          <Link
            to="/join"
            className="font-medium text-indigo-600 hover:text-indigo-500"
          >
            회원가입
          </Link>
        </div>
      </div>
    </div>
  );
} 