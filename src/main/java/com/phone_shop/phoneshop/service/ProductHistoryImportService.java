package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;

public interface ProductHistoryImportService {
    ProductHistoryImport importProduct(ImportProductDTO dto);
}
