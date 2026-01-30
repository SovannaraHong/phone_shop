package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

public interface ProductService {
    Product create(Product product);

    Product findById(Long id);

    void setSellPrice(Long id, BigDecimal price);

    //TODO improve handle error
    void importProduct(ImportProductDTO dto);

    Map<Integer, String> uploadProduct(MultipartFile file);

    Product getModelIdAndColorId(Long modelId, Long colorId);
}
