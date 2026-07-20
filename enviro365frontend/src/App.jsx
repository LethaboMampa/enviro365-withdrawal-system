import { BrowserRouter, Routes, Route } from "react-router-dom";
import InvestorLogin from './pages/InvestorLogin';
import InvestorDashboardPage from "./pages/InvestorDashboardPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<InvestorLogin />} />
        <Route path="/investor-login" element={<InvestorLogin />} />
        <Route path="/InvestorDashboardPage" element={<InvestorDashboardPage />} />
        <Route path="*" element={<div>404 Not Found</div>} />
      </Routes>
    </BrowserRouter>
  );
}