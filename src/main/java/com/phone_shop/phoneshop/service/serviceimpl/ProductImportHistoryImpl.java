package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.ProductHistoryImportResponseDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.mapper.ProductHistoryImportMapper;
import com.phone_shop.phoneshop.repository.ProductHistoryImportRepository;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductHistoryImportService;
import com.phone_shop.phoneshop.service.ProductService;
import com.phone_shop.phoneshop.specification.ProductImportHistoryFilter;
import com.phone_shop.phoneshop.specification.ProductImportHistorySpec;
import com.phone_shop.phoneshop.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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

    @Override
    public Page<ProductHistoryImportResponseDTO> getProductHistory(Map<String, String> params) {

        ProductImportHistoryFilter productImportHistoryFilter = new ProductImportHistoryFilter();

        ProductImportHistorySpec productImportHistorySpec =
                new ProductImportHistorySpec(productImportHistoryFilter);

        Pageable pageable = PageUtil.getPageable(params);

        Page<ProductHistoryImport> productHistroyImport =
                productHistoryImportRepository.findAll(productImportHistorySpec, pageable);

        return productHistroyImport.map(productHistoryImportMapper::toResponse);
    }

}
