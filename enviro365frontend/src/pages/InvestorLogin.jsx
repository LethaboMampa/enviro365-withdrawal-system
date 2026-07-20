import React, { useState } from 'react';
import '../style/login.css';
import { loginInvestor } from '../api/api';
import { useNavigate } from 'react-router-dom';

function InvestorLogin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoading(true);
    setError(null);

    try {
      const response = await loginInvestor({
        username,
        password
      });

      // backend returns investor info
      const investor = response.data;

      // IMPORTANT: store ID for dashboard usage
      localStorage.setItem('investorId', investor.id);

      // optional: store name/email if needed later
      localStorage.setItem('investorName', investor.fullName);
      localStorage.setItem('investorEmail', investor.username);

      // redirect to dashboard
      navigate('/InvestorDashboardPage');

    } catch (err) {
      setError(
        err.response?.data?.message ||
        'Login failed. Please check your credentials.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">

        <h2>Investor Login</h2>

        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label>Username (Email / ID Number)</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              placeholder="Enter email or ID number"
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter password"
            />
          </div>

          {error && (
            <p className="error">{error}</p>
          )}

          <button
            type="submit"
            className="btn investor-btn"
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>

        </form>

      </div>
    </div>
  );
}

export default InvestorLogin;