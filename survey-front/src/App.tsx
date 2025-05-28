import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Layout from './components/layout/Layout';
import LoginForm from './components/auth/LoginForm';
import JoinForm from './components/auth/JoinForm';
import SurveyList from './components/survey/SurveyList';
import SurveyDetail from './components/survey/SurveyDetail';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <Layout>
          <Routes>
            <Route path="/login" element={<LoginForm />} />
            <Route path="/join" element={<JoinForm />} />
            <Route path="/surveys" element={<SurveyList />} />
            <Route path="/surveys/:surveyId" element={<SurveyDetail />} />
            <Route path="/" element={<SurveyList />} />
          </Routes>
        </Layout>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
