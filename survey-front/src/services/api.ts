import axios from 'axios';
import type { Survey, SurveyRequest, PageDto } from '../types/survey';
import type { UserRequest, UserResponse, AdminLoginRequest, AdminLoginResponse, AdminUsersResponse } from '../types/user';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'Referrer-Policy': 'strict-origin-when-cross-origin'
  },
});

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Handle unauthorized error (e.g., redirect to login)
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Survey APIs
export const surveyApi = {
  getSurveyList: (page: number, size: number, search: string) =>
    api.get<PageDto<Survey>>(`/surveys?page=${page}&size=${size}&search=${search}`),
  
  getSurvey: (surveyId: number) =>
    api.get<Survey>(`/surveys/${surveyId}`),
  
  createSurvey: (data: SurveyRequest) =>
    api.post<Survey>('/surveys', data),
  
  updateSurvey: (surveyId: number, data: SurveyRequest) =>
    api.put<Survey>(`/surveys/${surveyId}`, data),
  
  updateSurveyPart: (surveyId: number, data: SurveyRequest) =>
    api.patch<Survey>(`/surveys/${surveyId}`, data),
  
  deleteSurvey: (surveyId: number) =>
    api.delete(`/surveys/${surveyId}`),
};

// User APIs
export const userApi = {
  join: (data: UserRequest) =>
    api.post<UserResponse>('/user/join', data),
  
  login: (data: UserRequest) =>
    api.post<UserResponse>('/user/login', data),
  
  logout: () =>
    api.post('/user/logout'),
};

// Admin APIs
export const adminApi = {
  login: (data: AdminLoginRequest) =>
    api.post<AdminLoginResponse>('/admin/login', data),
  
  logout: () =>
    api.post('/admin/logout'),
  
  getUsers: () =>
    api.get<PageDto<AdminUsersResponse>>('/admin/users'),
}; 