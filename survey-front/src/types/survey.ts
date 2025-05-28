export interface Survey {
  surveyId: number;
  surveyTitle: string;
  surveyVersion: string;
  createdDate: string;
  updatedDate: string;
  usedYn: boolean;
  questions: Question[];
}

export interface Question {
  questionId: number;
  questionType: 'SINGLE' | 'MULTIPLE' | 'TEXT';
  question: string;
  order: number;
  isRequired: boolean;
  answers: SurveyAnswer[];
}

export interface SurveyAnswer {
  answerId: number;
  answer: string;
}

export interface PageDto<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
}

export interface SurveyRequest {
  surveyId?: number;
  surveyTitle: string;
  surveyVersion: string;
  usedYn: boolean;
  questions: {
    questionId?: number;
    questionType: 'SINGLE' | 'MULTIPLE' | 'TEXT';
    question: string;
    order: number;
    isRequired: boolean;
    answers: {
      answerId?: number;
      answer: string;
    }[];
  }[];
} 