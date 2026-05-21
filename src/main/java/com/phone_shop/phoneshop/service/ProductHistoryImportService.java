package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.ProductHistoryImportResponseDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ProductHistoryImportService {
    ProductHistoryImport importProduct(ImportProductDTO dto);

    Page<ProductHistoryImportResponseDTO> getProductHistory(Map<String, String> params);

}
