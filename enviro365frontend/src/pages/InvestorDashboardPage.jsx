import React, { useState, useEffect } from 'react';
import {
  FiUser,
  FiBriefcase,
  FiDollarSign,
  FiLogOut,
  FiFileText
} from 'react-icons/fi';

import { useNavigate } from 'react-router-dom';
import { getInvestor, getPortfoliosByInvestor, updateInvestor , createWithdrawal,getWithdrawalsByPortfolio ,generateWithdrawalReport ,exportCSV} from '../api/api';
import '../style/investor-dashboard.css';

function InvestorDashboardPage() {
  const [investor, setInvestor] = useState({});
  const [portfolios, setPortfolios] = useState([]);
  const [activeSection, setActiveSection] = useState('portfolios');
  const [expandedPortfolio, setExpandedPortfolio] = useState(null);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const [showForm, setShowForm] = useState(false);
  const [withdrawals, setWithdrawals] = useState([]);
  const [selectedPortfolio, setSelectedPortfolio] = useState('');
  const [amount, setAmount] = useState('');
  const [reason, setReason] = useState('');
  const [withdrawMessage, setWithdrawMessage] = useState('');

  const [reportData, setReportData] = useState([]);
  const [fromDate, setFromDate] = useState('');
  const [toDate, setToDate] = useState('');
  const [status, setStatus] = useState('');
  const [amountFilterType, setAmountFilterType] = useState('GT');
  const [reportLoading, setReportLoading] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    loadInvestor();
  }, []);

  useEffect(() => {
  if (portfolios.length > 0) {
    loadWithdrawals();
  }
}, [portfolios]);


  const loadInvestor = async () => {
    const id = localStorage.getItem("investorId");

    const investorRes = await getInvestor(id);
        setInvestor(investorRes.data);

    const portfolioRes = await getPortfoliosByInvestor(id);
    const data = portfolioRes.data;
    setPortfolios(Array.isArray(data) ? data : []);
  };

  const handleLogout = () => {
    localStorage.removeItem("investorId");
    navigate('/investor-login');
  };

  const toggleExpand = (id) => {
  setExpandedPortfolio(prev => (prev === id ? null : id));
};

  const handleSave = async () => {
  try {
    setLoading(true);

    const id = localStorage.getItem("investorId");

    const payload = {
      fullName: investor.fullName,
      phone: investor.phone,
      address: investor.address
    };

    await updateInvestor(id, payload);

    setMessage("Profile updated successfully");

    // refresh data
    await loadInvestor();

  } catch (err) {
    setMessage(err.response?.data?.message || "Update failed ❌");
  } finally {
    setLoading(false);
  }
};

  const loadWithdrawals = async () => {

    console.log("Portfolios:", portfolios);

    for (let p of portfolios) {
      console.log("Portfolio ID:", p.id);
}

  try {
    if (!portfolios || portfolios.length === 0) {
      return;
    }

    const all = [];

    for (const p of portfolios) {
      if (!p.id) continue; // skip invalid portfolios

      const res = await getWithdrawalsByPortfolio(p.id);

      if (Array.isArray(res.data)) {
        all.push(...res.data);
      }
    }

    setWithdrawals(all);

  } catch (err) {
    console.error("Withdrawal load error:", err);
  }
};

const submitWithdrawal = async () => {
  try {
    setWithdrawMessage('');

    if (!selectedPortfolio) {
      setWithdrawMessage("Please select a portfolio");
      return;
    }

    const payload = {
      portfolioId: Number(selectedPortfolio),
      amount: Number(amount),
      reason
    };

    await createWithdrawal(payload);

    setWithdrawMessage('Withdrawal submitted successfully ✅');

    setAmount('');
    setReason('');
    setShowForm(false);

    await loadWithdrawals(selectedPortfolio);
  } catch (err) {
    setWithdrawMessage(err.response?.data?.message || 'Withdrawal failed ❌');
  }
};

const handleGenerateReport = async () => {
  try {
    setReportLoading(true);

    const investorId = localStorage.getItem("investorId");

    if (!investorId) {
      navigate("/investor-login");
      return;
    }

    const filters = {
      fromDate: fromDate || null,
      toDate: toDate || null,
      status: status || null,
      amount: amount === "" ? null : Number(amount),
      amountFilterType: amount === "" ? null : amountFilterType
    };

    console.log("CSV FILTERS SENT:", filters);

    const response = await exportCSV(
      investorId,
      filters
    );

    const blob = new Blob(
      [response.data],
      { type: "text/csv;charset=utf-8;" }
    );

    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");

    link.href = url;
    link.setAttribute(
      "download",
      `withdrawal_report_${investorId}.csv`
    );

    document.body.appendChild(link);
    link.click();
    link.remove();

    window.URL.revokeObjectURL(url);

  } catch (error) {
    console.error("CSV DOWNLOAD ERROR:", error);
    console.error("RESPONSE ERROR DATA:", error.response?.data);

    setMessage(
      error.response?.data?.message ||
      "Unable to download CSV report"
    );
  } finally {
    setReportLoading(false);
  }
};

const handleGenerateTableReport = async () => {
  try {
    setReportLoading(true);

    const investorId = localStorage.getItem("investorId");

    if (!investorId) {
      navigate("/investor-login");
      return;
    }

    const filters = {
      fromDate: fromDate || null,
      toDate: toDate || null,
      status: status || null,
      amount: amount === "" ? null : Number(amount),
      amountFilterType: amount === "" ? null : amountFilterType
    };

    console.log("INVESTOR ID:", investorId);
    console.log("FILTERS SENT:", filters);

    const response = await generateWithdrawalReport(
      investorId,
      filters
    );

    console.log("STATUS:", response.status);
    console.log("HEADERS:", response.headers);
    console.log("DATA:", response.data);

    setReportData(
      Array.isArray(response.data) ? response.data : []
    );

  } catch (error) {
    console.error("FULL ERROR:", error);
    console.error("RESPONSE ERROR DATA:", error.response?.data);

    setReportData([]);

    setMessage(
      error.response?.data?.message ||
      "Unable to generate report"
    );
  } finally {
    setReportLoading(false);
  }
};



  return (
    <div className="dashboard-wrapper">

      {/* ================= TOP NAVBAR ================= */}
      <header className="top-navbar">

        {/* LEFT */}
        <div className="nav-left">
          <FiUser />
          <span>{investor.fullName}</span>
        </div>

        {/* CENTER NAVIGATION */}
        <div className="nav-center">

          <button
            className={activeSection === 'profile' ? 'active' : ''}
            onClick={() => setActiveSection('profile')}
          >
            <FiUser /> Profile
          </button>

          <button
            className={activeSection === 'portfolios' ? 'active' : ''}
            onClick={() => setActiveSection('portfolios')}
          >
            <FiBriefcase /> Portfolios
          </button>

          <button
            className={activeSection === 'withdrawals' ? 'active' : ''}
            onClick={() => {setActiveSection('withdrawals');
               loadWithdrawals(); // load ONLY when opening tab
            }}
          >
            <FiDollarSign /> Withdrawals
          </button>

          <button
            className={activeSection === 'report' ? 'active' : ''}
            onClick={() => setActiveSection('report')}
          >
            <FiFileText /> Generate Report
          </button>

        </div>

        {/* RIGHT */}
        <div className="nav-right">
          <span>{investor.email}</span>
          <button className="logout-btn" onClick={handleLogout}>
            <FiLogOut /> Logout
          </button>
        </div>

      </header>

      {/* ================= MAIN CONTENT ================= */}
      <main className="dashboard-main">

        {/* ================= PORTFOLIOS ================= */}
        {activeSection === 'portfolios' && (
          <div className="section">

            <h2>My Portfolios</h2>

            <table className="portfolio-table">
              <thead>
                <tr>
                  <th>Portfolio Name</th>
                  <th>Balance</th>
                  <th>Action</th>
                </tr>
              </thead>

              <tbody>
                {portfolios.length === 0 ? (
                  <tr>
                    <td colSpan="3" className="no-data">
                      No portfolios available
                    </td>
                  </tr>
                ) : (
                  portfolios.map((p) => (
                    <React.Fragment key={p.id ?? p.portfolioName}>
                      <tr>
                        <td>{p.portfolioName}</td>
                        <td>R {p.balance}</td>
                        <td>
                          <button
                            className="expand-btn"
                            onClick={() => toggleExpand(p.id)}
                          >
                            +
                          </button>
                        </td>
                      </tr>

                      {expandedPortfolio === p.id && (
                        <tr className="product-row">
                          <td colSpan="3">

                            <h4>Products</h4>

                            {p.products?.length > 0 ? (
                              <table className="product-table">
                                <thead>
                                  <tr>
                                    <th>Name</th>
                                    <th>Type</th>
                                    <th>Units</th>
                                    <th>Price</th>
                                    <th>Value</th>
                                  </tr>
                                </thead>

                                <tbody>
                                  {p.products.map(prod => (
                                    <tr key={prod.id}>
                                      <td>{prod.productName}</td>
                                      <td>{prod.productType}</td>
                                      <td>{prod.units}</td>
                                      <td>R {prod.unitPrice}</td>
                                      <td>R {prod.units * prod.unitPrice}</td>
                                    </tr>
                                  ))}
                                </tbody>
                              </table>
                            ) : (
                              <p className="no-data">No products available</p>
                            )}

                          </td>
                        </tr>
                      )}
                    </React.Fragment>
                  ))
                )}
              </tbody>
            </table>

          </div>
        )}

        {/* ================= PROFILE ================= */}
       {activeSection === 'profile' && (
  <div className="profile-wrapper">

    <div className="profile-card">

      <h2>Investor Profile</h2>

      <div className="profile-grid">

        {/* FULL NAME (editable) */}
        <div className="field">
          <label>Full Name</label>
          <input
            value={investor.fullName || ''}
            onChange={(e) =>
              setInvestor({ ...investor, fullName: e.target.value })
            }
          />
        </div>

        {/* EMAIL (READ ONLY) */}
        <div className="field">
          <label>Email (Read Only)</label>
          <input value={investor.email || ''} disabled />
        </div>

        {/* PHONE (editable) */}
        <div className="field">
          <label>Phone</label>
          <input
            value={investor.phone || ''}
            onChange={(e) =>
              setInvestor({ ...investor, phone: e.target.value })
            }
          />
        </div>

        {/* ID NUMBER (READ ONLY) */}
        <div className="field">
          <label>ID Number (Read Only)</label>
          <input value={investor.idNumber || ''} disabled />
        </div>

        {/* ADDRESS (editable) */}
        <div className="field">
          <label>Address</label>
          <input
            value={investor.address || ''}
            onChange={(e) =>
              setInvestor({ ...investor, address: e.target.value })
            }
          />
        </div>

     

      </div>

      <p className="admin-note">
        Only Admin can modify email, ID number, and date of birth.
      </p>
       
      <button className="save-btn" onClick={handleSave} disabled={loading}>
  {loading ? "Saving..." : "Save Changes"}
</button>
{message && <p className="save-message">{message}</p>}

    </div>

  </div>
)}

        {/* ================= WITHDRAWAL================= */}
        {activeSection === 'withdrawals' && (
  <div className="withdrawal-section">

    <h2>Withdrawals</h2>

    {/* BUTTON */}
    <button
      className="request-btn"
      onClick={() => setShowForm(!showForm)}
    >
      Request Withdrawal
    </button>

    {/* FORM */}
    {showForm && (
      <div className="withdrawal-form">

        <select
          value={selectedPortfolio}
          onChange={(e) => {
  const id = e.target.value;
  setSelectedPortfolio(id);
  loadWithdrawals(id);
}}
        >
          <option value="">Select Portfolio</option>
          {portfolios.map(p => (
            <option key={p.id} value={p.id}>
              {p.portfolioName} (R {p.balance})
            </option>
          ))}
        </select>

        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <input
          type="text"
          placeholder="Reason"
          value={reason}
          onChange={(e) => setReason(e.target.value)}
        />

        <button onClick={submitWithdrawal}>
          Submit
        </button>

        {withdrawMessage && (
          <p className="message">{withdrawMessage}</p>
        )}

      </div>
    )}

    {/* HISTORY TABLE */}
    <table className="portfolio-table">
      <thead>
        <tr>
          <th>Amount</th>
          <th>Reason</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        {withdrawals.length === 0 ? (
          <tr>
            <td colSpan="3" className="no-data">
              No withdrawals yet
            </td>
          </tr>
        ) : (
          withdrawals.map(w => (
            <tr key={w.id}>
              <td>R {w.amount}</td>
              <td>{w.reason}</td>
              <td>{w.status}</td>
            </tr>
          ))
        )}
      </tbody>
    </table>

  </div>
)}


  {/* ================= PROFILE ================= */}
  {activeSection === 'report' && (
  <div className="section">

    <h2>Generate Withdrawal Report</h2>

    {/* FILTERS */}
    <div className="report-filters">

      <input
        type="date"
        value={fromDate}
        onChange={(e) => setFromDate(e.target.value)}
      />

      <input
        type="date"
        value={toDate}
        onChange={(e) => setToDate(e.target.value)}
      />

      <select value={status} onChange={(e) => setStatus(e.target.value)}>
        <option value="">All Status</option>
        <option value="PENDING">PENDING</option>
        <option value="APPROVED">APPROVED</option>
        <option value="REJECTED">REJECTED</option>
      </select>

      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      <select
        value={amountFilterType}
        onChange={(e) => setAmountFilterType(e.target.value)}
      >
        <option value="GT">Greater Than</option>
        <option value="LT">Less Than</option>
        <option value="EQ">Equal</option>
      </select>

    <button
  onClick={handleGenerateTableReport}
  disabled={reportLoading}
>
  {reportLoading ? "Generating..." : "Generate Report"}
</button>

<button
  onClick={handleGenerateReport}
  disabled={reportLoading}
>
  {reportLoading ? "Preparing..." : "Download CSV"}
</button>

    </div>

    {/* TABLE */}
    <table className="portfolio-table">

      <thead>
        <tr>
          <th>Investor</th>
          <th>Portfolio</th>
          <th>Amount</th>
          <th>Status</th>
          <th>Date</th>
          <th>Products</th>
        </tr>
      </thead>

      <tbody>
        {reportData.length === 0 ? (
          <tr>
            <td colSpan="6" className="no-data">
              No report data
            </td>
          </tr>
        ) : (
          reportData.map((r) => (
            <tr key={r.withdrawalId}>
              <td>{r.investorName}</td>
              <td>{r.portfolioName}</td>
              <td>R {r.amount}</td>
              <td>{r.status}</td>
              <td>{new Date(r.withdrawalDate).toLocaleDateString()}</td>
              <td>{r.products}</td>
            </tr>
          ))
        )}
      </tbody>

    </table>

  </div>
)}

      </main>
    </div>
  );
}

export default InvestorDashboardPage;