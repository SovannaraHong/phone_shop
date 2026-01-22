package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.mapper.ProductHistoryImportMapper;
import com.phone_shop.phoneshop.repository.ProductHistoryImportRepository;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductHistoryImportService;
import com.phone_shop.phoneshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImportHistoryImpl implements ProductHistoryImportService {

    private final ProductHistoryImportRepository productHistoryImportRepository;
    private final ProductService productService;
    private final ProductHistoryImportMapper productHistoryImportMapper;
    private final ProductRepository productRepository;

    //TODO improve exception handler
    @Override
    public ProductHistoryImport importProduct(ImportProductDTO dto) {
        // save

        Product productId = productService.findById(dto.getProductId());

        Integer availableUnit = 0;
        if (dto.getImportUnit() != null) {
            availableUnit = productId.getUnit();
        }
        if (productId.getUnit() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "unit can not be empty");
        }
        productId.setUnit(availableUnit + dto.getImportUnit());

        productRepository.save(productId);

        //update product unit
        ProductHistoryImport product = productHistoryImportMapper.toProduct(dto);
        return productHistoryImportRepository.save(product);

    }
}
