package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.dto.ProductResponseDTO;
import com.phone_shop.phoneshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Product getProductById(Long id);

    Product getProductByName(String name);

    Product updateProduct(ProductDTO productDTO, Long id);

    void deleteProduct(Long id);

    List<Product> getProducts();

    Page<ProductResponseDTO> getProducts(Map<String, String> params);

    Product create(Product product, Long modelId, Long colorId);

    Product findById(Long id);


    void setSellPrice(Long id, BigDecimal price);

    //TODO improve handle error
    void importProduct(ImportProductDTO dto);

    Map<Integer, String> uploadProduct(MultipartFile file);

    Product getModelIdAndColorId(Long modelId, Long colorId);
}
