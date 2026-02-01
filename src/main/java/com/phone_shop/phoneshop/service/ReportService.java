package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.reports.ExpenseReportDTO;
import com.phone_shop.phoneshop.dto.reports.ProductReportDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ProductReportDTO> productReportSold(LocalDateTime startDate, LocalDateTime endDate);

    List<ExpenseReportDTO> expenseReport(LocalDate startDate, LocalDate endDate);
}
