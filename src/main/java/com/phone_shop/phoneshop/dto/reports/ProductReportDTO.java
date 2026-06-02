package com.phone_shop.phoneshop.dto.reports;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductReportDTO {

    private Long productId;
    private String productName;
    private Integer productUnit;
    private BigDecimal totalAmount;
    private LocalDateTime soldDate;
    private String imagePath;
}
