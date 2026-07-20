import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' }
});

// AUTH
export const loginInvestor = (data) => api.post('/auth/login/investor', data);

// INVESTOR
export const getInvestor = (id) => api.get(`/investors/${id}`);
export const getInvestors = () => api.get('/investors');

// PORTFOLIO
export const getPortfoliosByInvestor = (investorId) =>
  api.get(`/portfolios/investor/${investorId}`);

export const createPortfolio = (data) =>
  api.post('/portfolios', data);

// WITHDRAWALS
export const createWithdrawal = (data) =>
  api.post('/withdrawals', data);

export const getWithdrawalsByPortfolio = (portfolioId) =>
  api.get(`/withdrawals/portfolio/${portfolioId}`);

export const exportCSV = (investorId, filters) =>
  api.post(
    `/withdrawals/export/${investorId}`,
    filters,
    { responseType: "blob" }
  );

export const updateInvestor = (id, data) =>
  api.put(`/investors/${id}`, data);

export const generateWithdrawalReport = (investorId, filters) =>
  api.post(`/withdrawals/report/${investorId}`, filters);

export default api;