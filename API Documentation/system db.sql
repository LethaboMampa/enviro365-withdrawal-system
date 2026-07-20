-- ============================================================
-- ENVIRO365 WITHDRAWAL SYSTEM
-- SAMPLE DATABASE DATA
-- ============================================================
-- This script inserts sample data into:
--   1. INVESTORS
--   2. PORTFOLIOS
--   3. PRODUCTS
--   4. WITHDRAWALS
-------------------

-- Important:
-- Run the inserts in this order because portfolios depend on
-- investors, products depend on portfolios, and withdrawals
-- depend on portfolios.
------------------------

-- The passwords below are for demonstration purposes only.
-- ============================================================

-- ============================================================
-- IF EXISTS: CLEAR EXISTING DATA
-- ============================================================
-- Uncomment these statements when you want to reset the
-- database before inserting the sample records.
------------------------------------------------

DELETE FROM WITHDRAWALS;
DELETE FROM PRODUCTS;
DELETE FROM PORTFOLIOS;
DELETE FROM INVESTORS;

-- ============================================================
-- 1. INVESTORS
-- ============================================================
-- Some investors are older than 65 and should qualify for
-- retirement withdrawals.
--------------------------

-- Other investors are younger than 65 and can be used to test
-- the age validation business rule.
-- ============================================================

INSERT INTO INVESTORS (ID,ID_NUMBER,FULL_NAME,
EMAIL,
PASSWORD,
DATE_OF_BIRTH,
ADDRESS,
PHONE,
CREATED_AT
)
VALUES
(
1,
'5501015009087',
'Lethabo Mampa',
'mampacalvin4@gmail.com',
'1234',
'1955-01-01',
'25 Nelson Mandela Drive, Pretoria, Gauteng',
'0821110001',
CURRENT_TIMESTAMP
),
(
2,
'5202025009088',
'Jane Smith',
'jane.smith@mail.com',
'1234',
'1952-02-02',
'18 Rivonia Road, Sandton, Gauteng',
'0821110002',
CURRENT_TIMESTAMP
),
(
3,
'4803035009089',
'Thabo Mokoena',
'thabo.mokoena@mail.com',
'1234',
'1948-03-03',
'77 Church Street, Polokwane, Limpopo',
'0821110003',
CURRENT_TIMESTAMP
),
(
4,
'6004045009090',
'Alice Brown',
'alice.brown@mail.com',
'1234',
'1960-04-04',
'10 Long Street, Cape Town, Western Cape',
'0821110004',
CURRENT_TIMESTAMP
),
(
5,
'5805055009091',
'Nomsa Dlamini',
'nomsa.dlamini@mail.com',
'1234',
'1958-05-05',
'42 Smith Street, Durban, KwaZulu-Natal',
'0821110005',
CURRENT_TIMESTAMP
),
(
6,
'4906065009092',
'Peter Johnson',
'peter.johnson@mail.com',
'1234',
'1949-06-06',
'12 Main Road, Gqeberha, Eastern Cape',
'0821110006',
CURRENT_TIMESTAMP
),
(
7,
'8707075009093',
'Bob Johnson',
'bob.johnson@mail.com',
'1234',
'1987-07-07',
'31 Market Street, Johannesburg, Gauteng',
'0821110007',
CURRENT_TIMESTAMP
),
(
8,
'8808085009094',
'Lerato Molefe',
'lerato.molefe@mail.com',
'1234',
'1988-08-08',
'55 Paul Kruger Street, Pretoria, Gauteng',
'0821110008',
CURRENT_TIMESTAMP
),
(
9,
'9009095009095',
'Michael Williams',
'michael.williams@mail.com',
'1234',
'1990-09-09',
'9 Oxford Road, Rosebank, Gauteng',
'0821110009',
CURRENT_TIMESTAMP
),
(
10,
'9210105009096',
'Zanele Khumalo',
'zanele.khumalo@mail.com',
'1234',
'1992-10-10',
'83 West Street, Durban, KwaZulu-Natal',
'0821110010',
CURRENT_TIMESTAMP
);

-- ============================================================
-- 2. PORTFOLIOS
-- ============================================================
-- Valid PORTFOLIO_TYPE values:
--   RETIREMENT
--   SAVINGS
--   INVESTMENT
-- ============================================================

INSERT INTO PORTFOLIOS (
ID,
BALANCE,
CREATED_AT,
INVESTOR_ID,
PORTFOLIO_NAME,
PORTFOLIO_TYPE
)
VALUES
(
1,
100000.00,
CURRENT_TIMESTAMP,
1,
'Lethabo Retirement Fund',
'RETIREMENT'
),
(
2,
50000.00,
CURRENT_TIMESTAMP,
1,
'Lethabo Growth Portfolio',
'INVESTMENT'
),
(
3,
250000.00,
CURRENT_TIMESTAMP,
2,
'Jane Retirement Plan',
'RETIREMENT'
),
(
4,
75000.00,
CURRENT_TIMESTAMP,
2,
'Jane Emergency Savings',
'SAVINGS'
),
(
5,
180000.00,
CURRENT_TIMESTAMP,
3,
'Thabo Pension Portfolio',
'RETIREMENT'
),
(
6,
90000.00,
CURRENT_TIMESTAMP,
3,
'Thabo Investment Account',
'INVESTMENT'
),
(
7,
125000.00,
CURRENT_TIMESTAMP,
4,
'Alice Retirement Fund',
'RETIREMENT'
),
(
8,
40000.00,
CURRENT_TIMESTAMP,
4,
'Alice Savings Account',
'SAVINGS'
),
(
9,
210000.00,
CURRENT_TIMESTAMP,
5,
'Nomsa Retirement Portfolio',
'RETIREMENT'
),
(
10,
60000.00,
CURRENT_TIMESTAMP,
5,
'Nomsa Balanced Investment',
'INVESTMENT'
),
(
11,
300000.00,
CURRENT_TIMESTAMP,
6,
'Peter Retirement Fund',
'RETIREMENT'
),
(
12,
85000.00,
CURRENT_TIMESTAMP,
7,
'Bob Investment Portfolio',
'INVESTMENT'
),
(
13,
45000.00,
CURRENT_TIMESTAMP,
8,
'Lerato Savings Portfolio',
'SAVINGS'
),
(
14,
110000.00,
CURRENT_TIMESTAMP,
9,
'Michael Growth Investment',
'INVESTMENT'
),
(
15,
35000.00,
CURRENT_TIMESTAMP,
10,
'Zanele Savings Account',
'SAVINGS'
);

-- ============================================================
-- 3. PRODUCTS
-- ============================================================
-- Valid PRODUCT_TYPE values:
--   RETIREMENT
--   EQUITY
--   BOND
--   CASH
---------

-- Product value can be calculated as:
--   UNITS * UNIT_PRICE
-- ============================================================

INSERT INTO PRODUCTS (
ID,
PRODUCT_NAME,
PRODUCT_TYPE,
UNITS,
UNIT_PRICE,
CREATED_AT,
PORTFOLIO_ID
)
VALUES
(
1,
'Old Mutual Retirement Annuity',
'RETIREMENT',
20,
2500.00,
CURRENT_TIMESTAMP,
1
),
(
2,
'S&P 500 ETF',
'EQUITY',
10,
1500.00,
CURRENT_TIMESTAMP,
1
),
(
3,
'South African Government Bond',
'BOND',
35,
1000.00,
CURRENT_TIMESTAMP,
1
),
(
4,
'Naspers Shares',
'EQUITY',
5,
3200.00,
CURRENT_TIMESTAMP,
2
),
(
5,
'Money Market Cash Fund',
'CASH',
34,
1000.00,
CURRENT_TIMESTAMP,
2
),
(
6,
'Sanlam Retirement Annuity',
'RETIREMENT',
50,
3000.00,
CURRENT_TIMESTAMP,
3
),
(
7,
'Government Retail Bond',
'BOND',
50,
2000.00,
CURRENT_TIMESTAMP,
3
),
(
8,
'Fixed Deposit Cash Fund',
'CASH',
50,
1500.00,
CURRENT_TIMESTAMP,
4
),
(
9,
'Momentum Pension Fund',
'RETIREMENT',
40,
3000.00,
CURRENT_TIMESTAMP,
5
),
(
10,
'Balanced Bond Fund',
'BOND',
30,
2000.00,
CURRENT_TIMESTAMP,
5
),
(
11,
'Apple Shares',
'EQUITY',
10,
2800.00,
CURRENT_TIMESTAMP,
6
),
(
12,
'Microsoft Shares',
'EQUITY',
10,
3200.00,
CURRENT_TIMESTAMP,
6
),
(
13,
'Corporate Bond Fund',
'BOND',
30,
1000.00,
CURRENT_TIMESTAMP,
6
),
(
14,
'Discovery Retirement Fund',
'RETIREMENT',
25,
3000.00,
CURRENT_TIMESTAMP,
7
),
(
15,
'Government Bond Fund',
'BOND',
25,
2000.00,
CURRENT_TIMESTAMP,
7
),
(
16,
'High Interest Savings Fund',
'CASH',
40,
1000.00,
CURRENT_TIMESTAMP,
8
),
(
17,
'Liberty Retirement Annuity',
'RETIREMENT',
45,
3000.00,
CURRENT_TIMESTAMP,
9
),
(
18,
'Inflation Linked Bond',
'BOND',
25,
2000.00,
CURRENT_TIMESTAMP,
9
),
(
19,
'JSE Top 40 ETF',
'EQUITY',
20,
2000.00,
CURRENT_TIMESTAMP,
9
),
(
20,
'Allan Gray Equity Fund',
'EQUITY',
15,
2500.00,
CURRENT_TIMESTAMP,
10
),
(
21,
'Corporate Cash Fund',
'CASH',
15,
1500.00,
CURRENT_TIMESTAMP,
10
),
(
22,
'Alexander Forbes Pension Fund',
'RETIREMENT',
60,
3500.00,
CURRENT_TIMESTAMP,
11
),
(
23,
'Long-Term Government Bond',
'BOND',
45,
2000.00,
CURRENT_TIMESTAMP,
11
),
(
24,
'Tesla Shares',
'EQUITY',
10,
2500.00,
CURRENT_TIMESTAMP,
12
),
(
25,
'Amazon Shares',
'EQUITY',
10,
3000.00,
CURRENT_TIMESTAMP,
12
),
(
26,
'Cash Reserve Fund',
'CASH',
30,
1000.00,
CURRENT_TIMESTAMP,
12
),
(
27,
'Tax-Free Savings Fund',
'CASH',
25,
1000.00,
CURRENT_TIMESTAMP,
13
),
(
28,
'Domestic Bond Fund',
'BOND',
20,
1000.00,
CURRENT_TIMESTAMP,
13
),
(
29,
'Global Equity ETF',
'EQUITY',
20,
2500.00,
CURRENT_TIMESTAMP,
14
),
(
30,
'JSE Dividend Fund',
'EQUITY',
20,
2000.00,
CURRENT_TIMESTAMP,
14
),
(
31,
'Investment Bond',
'BOND',
20,
1000.00,
CURRENT_TIMESTAMP,
14
),
(
32,
'Emergency Cash Fund',
'CASH',
20,
1000.00,
CURRENT_TIMESTAMP,
15
),
(
33,
'Short-Term Bond Fund',
'BOND',
15,
1000.00,
CURRENT_TIMESTAMP,
15
);

-- ============================================================
-- 4. WITHDRAWALS
-- ============================================================
-- Valid STATUS values:
--   PENDING
--   APPROVED
--   REJECTED
-------------

-- These records provide a mixture of withdrawal statuses for
-- dashboard, history, filtering, and CSV export testing.
-- ============================================================

INSERT INTO WITHDRAWALS (
ID,
AMOUNT,
PORTFOLIO_ID,
WITHDRAWAL_DATE,
REASON,
STATUS
)
VALUES
(
1,
1000.00,
1,
'2026-06-01',
'Monthly living expenses',
'APPROVED'
),
(
2,
2500.00,
1,
'2026-06-05',
'Medical expenses',
'APPROVED'
),
(
3,
5000.00,
2,
'2026-06-08',
'Home maintenance',
'PENDING'
),
(
4,
15000.00,
3,
'2026-06-10',
'Retirement living costs',
'APPROVED'
),
(
5,
20000.00,
3,
'2026-06-12',
'Family support',
'PENDING'
),
(
6,
70000.00,
4,
'2026-06-14',
'Large savings withdrawal',
'REJECTED'
),
(
7,
12000.00,
5,
'2026-06-16',
'Property repairs',
'APPROVED'
),
(
8,
18000.00,
5,
'2026-06-18',
'Medical procedure',
'PENDING'
),
(
9,
5000.00,
6,
'2026-06-20',
'Education expenses',
'APPROVED'
),
(
10,
25000.00,
7,
'2026-06-22',
'Vehicle purchase',
'PENDING'
),
(
11,
115000.00,
7,
'2026-06-23',
'Withdrawal exceeding allowed limit',
'REJECTED'
),
(
12,
3000.00,
8,
'2026-06-24',
'Emergency household expenses',
'APPROVED'
),
(
13,
10000.00,
9,
'2026-06-25',
'Monthly retirement payment',
'APPROVED'
),
(
14,
35000.00,
9,
'2026-06-26',
'Home renovation',
'PENDING'
),
(
15,
55000.00,
10,
'2026-06-27',
'Investment withdrawal request',
'REJECTED'
),
(
16,
20000.00,
11,
'2026-06-28',
'Retirement living expenses',
'APPROVED'
),
(
17,
45000.00,
11,
'2026-06-29',
'Property purchase deposit',
'PENDING'
),
(
18,
8000.00,
12,
'2026-07-01',
'Personal expenses',
'REJECTED'
),
(
19,
5000.00,
13,
'2026-07-02',
'Emergency savings withdrawal',
'REJECTED'
),
(
20,
12000.00,
14,
'2026-07-03',
'Business equipment',
'REJECTED'
),
(
21,
2500.00,
15,
'2026-07-04',
'Unexpected household expense',
'REJECTED'
),
(
22,
6000.00,
1,
'2026-07-05',
'Travel expenses',
'PENDING'
),
(
23,
9000.00,
3,
'2026-07-06',
'Insurance payment',
'APPROVED'
),
(
24,
11000.00,
5,
'2026-07-07',
'Family emergency',
'PENDING'
),
(
25,
15000.00,
9,
'2026-07-08',
'Retirement income payment',
'APPROVED'
);

-- ============================================================
-- VERIFICATION QUERIES
-- ============================================================

SELECT * FROM INVESTORS ORDER BY ID;

SELECT * FROM PORTFOLIOS ORDER BY ID;

SELECT * FROM PRODUCTS ORDER BY ID;

SELECT * FROM WITHDRAWALS ORDER BY ID;

-- ============================================================
-- RECORD COUNTS
-- ============================================================

SELECT COUNT(*) AS TOTAL_INVESTORS
FROM INVESTORS;

SELECT COUNT(*) AS TOTAL_PORTFOLIOS
FROM PORTFOLIOS;

SELECT COUNT(*) AS TOTAL_PRODUCTS
FROM PRODUCTS;

SELECT COUNT(*) AS TOTAL_WITHDRAWALS
FROM WITHDRAWALS;

-- ============================================================
-- USEFUL JOIN QUERY
-- ============================================================
-- Displays investors together with their portfolios.

SELECT
I.ID AS INVESTOR_ID,
I.FULL_NAME,
I.EMAIL,
I.DATE_OF_BIRTH,
P.ID AS PORTFOLIO_ID,
P.PORTFOLIO_NAME,
P.PORTFOLIO_TYPE,
P.BALANCE
FROM INVESTORS I
LEFT JOIN PORTFOLIOS P
ON I.ID = P.INVESTOR_ID
ORDER BY I.ID, P.ID;

-- ============================================================
-- PORTFOLIO PRODUCT SUMMARY
-- ============================================================

SELECT
P.ID AS PORTFOLIO_ID,
P.PORTFOLIO_NAME,
PR.PRODUCT_NAME,
PR.PRODUCT_TYPE,
PR.UNITS,
PR.UNIT_PRICE,
PR.UNITS * PR.UNIT_PRICE AS PRODUCT_TOTAL_VALUE
FROM PORTFOLIOS P
LEFT JOIN PRODUCTS PR
ON P.ID = PR.PORTFOLIO_ID
ORDER BY P.ID, PR.ID;

-- ============================================================
-- WITHDRAWAL HISTORY SUMMARY
-- ============================================================

SELECT
W.ID AS WITHDRAWAL_ID,
I.FULL_NAME AS INVESTOR_NAME,
P.PORTFOLIO_NAME,
W.AMOUNT,
W.WITHDRAWAL_DATE,
W.REASON,
W.STATUS
FROM WITHDRAWALS W
INNER JOIN PORTFOLIOS P
ON W.PORTFOLIO_ID = P.ID
INNER JOIN INVESTORS I
ON P.INVESTOR_ID = I.ID
ORDER BY W.WITHDRAWAL_DATE DESC;

ALTER TABLE INVESTORS
ALTER COLUMN ID RESTART WITH 11;

ALTER TABLE PORTFOLIOS
ALTER COLUMN ID RESTART WITH 16;

ALTER TABLE PRODUCTS
ALTER COLUMN ID RESTART WITH 34;

ALTER TABLE WITHDRAWALS
ALTER COLUMN ID RESTART WITH 26;
