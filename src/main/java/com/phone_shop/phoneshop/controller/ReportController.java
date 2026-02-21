package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.reports.ExpenseReportDTO;
import com.phone_shop.phoneshop.dto.reports.ProductReportDTO;
import com.phone_shop.phoneshop.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PreAuthorize("hasAnyAuthority('report')")

    @GetMapping("{startDate}/{endDate}")
    public ResponseEntity<?> productReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @PathVariable LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @PathVariable LocalDateTime endDate) {

        List<ProductReportDTO> list = reportService.productReportSold(startDate, endDate);
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyAuthority('report')")
    @GetMapping("expense/{startDate}/{endDate}")
    public ResponseEntity<?> expenseReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate endDate) {
        List<ExpenseReportDTO> expenseReportDTOS = reportService.expenseReport(startDate, endDate);
        return ResponseEntity.ok(expenseReportDTOS);

    }

}
