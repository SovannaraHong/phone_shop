package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.ProductReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ProductReportDTO> productReportSold(LocalDateTime startDate, LocalDateTime endDate);
}
