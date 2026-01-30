package com.phone_shop.phoneshop.specification;

import lombok.Data;

import java.time.LocalDateTime;

  
@Data
public class SaleDetailFilter {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
