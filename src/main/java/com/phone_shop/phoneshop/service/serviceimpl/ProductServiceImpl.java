package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        String name = "%s %s".formatted(product.getModel().getName(), product.getColor().getName());
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productId = productRepository.findById(id);
        return productId
                .orElseThrow(() -> new ResourceNotFoundException("product", id, "id"));


    }

}
