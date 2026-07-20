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

    @PostMapping("/export/{investorId}")
    public void exportCSV(
            @PathVariable Long investorId,
            @RequestBody ReportFilterDTO filter,
            HttpServletResponse response
    ) throws IOException {

        List<WithdrawalReportDTO> reports =
                withdrawalService.generateReport(investorId, filter);

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=withdrawal_report.csv"
        );

        try (PrintWriter writer = response.getWriter()) {

            writer.println(
                    "Investor ID,Investor Name,Email," +
                            "Portfolio ID,Portfolio Name,Portfolio Type,Balance," +
                            "Products,Withdrawal ID,Amount,Reason,Status,Date"
            );

            for (WithdrawalReportDTO report : reports) {

                writer.println(
                        csvValue(report.getInvestorId()) + "," +
                                csvValue(report.getInvestorName()) + "," +
                                csvValue(report.getInvestorEmail()) + "," +
                                csvValue(report.getPortfolioId()) + "," +
                                csvValue(report.getPortfolioName()) + "," +
                                csvValue(report.getPortfolioType()) + "," +
                                csvValue(report.getPortfolioBalance()) + "," +
                                csvValue(report.getProducts()) + "," +
                                csvValue(report.getWithdrawalId()) + "," +
                                csvValue(report.getAmount()) + "," +
                                csvValue(report.getReason()) + "," +
                                csvValue(report.getStatus()) + "," +
                                csvValue(report.getWithdrawalDate())
                );
            }

            writer.flush();
        }
    }

    @PostMapping("/report/{investorId}")
    public ResponseEntity<List<WithdrawalReportDTO>> generateReport(
            @PathVariable Long investorId,
            @RequestBody ReportFilterDTO filter) {

        return ResponseEntity.ok(
                withdrawalService.generateReport(investorId, filter)
        );
    }

    private String csvValue(Object value) {

        if (value == null) {
            return "";
        }

        String text = value.toString();

        // Escape quotation marks inside CSV values
        text = text.replace("\"", "\"\"");

        // Wrap every value in quotation marks
        return "\"" + text + "\"";
    }
}