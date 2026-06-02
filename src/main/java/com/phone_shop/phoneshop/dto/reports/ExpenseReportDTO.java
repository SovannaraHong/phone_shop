package com.phone_shop.phoneshop.dto.reports;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseReportDTO {
    private Long productId;
    private String productName;
    private Integer expenseUnit;
    private BigDecimal totalAmount;
    private LocalDateTime expenseDate;
}
