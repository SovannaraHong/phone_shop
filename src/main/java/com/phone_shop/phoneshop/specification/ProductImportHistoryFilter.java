package com.phone_shop.phoneshop.specification;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ProductImportHistoryFilter {
    private LocalDate startDate;
    private LocalDate endDate;
}
