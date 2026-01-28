package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.mapper.ProductHistoryImportMapper;
import com.phone_shop.phoneshop.repository.ProductHistoryImportRepository;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductHistoryImportService;
import com.phone_shop.phoneshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImportHistoryImpl implements ProductHistoryImportService {

    private final ProductHistoryImportRepository productHistoryImportRepository;
    private final ProductService productService;
    private final ProductHistoryImportMapper productHistoryImportMapper;
    private final ProductRepository productRepository;

    //TODO improve exception handler
    @Transactional
    @Override
    public ProductHistoryImport importProduct(ImportProductDTO dto) {

        //save or update to product
        Product product = productService.findById(dto.getProductId());
        int currentStock = product.getUnit() == null ? 0 : product.getUnit();
        product.setUnit(currentStock + dto.getImportUnit());
        productRepository.save(product);

        // save product history
        ProductHistoryImport productHistory = productHistoryImportMapper.toProduct(dto);
        productHistory.setProduct(product);
        return productHistoryImportRepository.save(productHistory);

    }


}
