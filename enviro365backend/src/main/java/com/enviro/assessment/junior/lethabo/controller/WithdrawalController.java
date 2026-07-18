package com.enviro.assessment.junior.lethabo.controller;

import com.enviro.assessment.junior.lethabo.dto.ReportFilterDTO;
import com.enviro.assessment.junior.lethabo.dto.WithdrawalReportDTO;
import com.enviro.assessment.junior.lethabo.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.lethabo.entity.Withdrawal;
import com.enviro.assessment.junior.lethabo.service.WithdrawalService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping
    public ResponseEntity<Withdrawal> submitWithdrawal(
            @Valid @RequestBody WithdrawalRequestDTO dto) {

        return ResponseEntity.ok(
                withdrawalService.submitWithdrawal(dto));
    }

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<Withdrawal>> getHistory(
            @PathVariable Long portfolioId) {

        return ResponseEntity.ok(
                withdrawalService.getWithdrawalHistory(portfolioId));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<Withdrawal>> getInvestorHistory(
            @PathVariable Long investorId) {

        return ResponseEntity.ok(
                withdrawalService.getInvestorWithdrawals(investorId));
    }

    @PostMapping("/export")
    public void exportCSV(
            @RequestBody ReportFilterDTO filter,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=withdrawal_report.csv");

        PrintWriter writer = response.getWriter();

        writer.println(
                "Investor ID,Investor Name,Email," +
                        "Portfolio ID,Portfolio Name,Portfolio Type,Balance," +
                        "Products,Withdrawal ID,Amount,Reason,Status,Date");

        List<WithdrawalReportDTO> reports =
                withdrawalService.generateReport(filter);

        for (WithdrawalReportDTO r : reports) {

            writer.println(
                    r.getInvestorId() + "," +
                            r.getInvestorName() + "," +
                            r.getInvestorEmail() + "," +
                            r.getPortfolioId() + "," +
                            r.getPortfolioName() + "," +
                            r.getPortfolioType() + "," +
                            r.getPortfolioBalance() + "," +
                            "\"" + r.getProducts() + "\"," +
                            r.getWithdrawalId() + "," +
                            r.getAmount() + "," +
                            "\"" + r.getReason() + "\"," +
                            r.getStatus() + "," +
                            r.getWithdrawalDate()
            );
        }

        writer.flush();
        writer.close();
    }

    @PostMapping("/report")
    public ResponseEntity<List<WithdrawalReportDTO>> generateReport(
            @RequestBody ReportFilterDTO filter) {

        return ResponseEntity.ok(
                withdrawalService.generateReport(filter)
        );
    }
}