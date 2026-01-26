package com.phone_shop.phoneshop.service.serviceimpl;


import com.phone_shop.phoneshop.dto.ProductSoldDTO;
import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.Sale;
import com.phone_shop.phoneshop.entity.SaleDetail;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.mapper.SaleMapper;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.repository.SaleDetailRepository;
import com.phone_shop.phoneshop.repository.SaleRepository;
import com.phone_shop.phoneshop.service.ProductService;
import com.phone_shop.phoneshop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final SaleDetailRepository saleDetailRepository;


    @Override
    public void sell(SaleDTO saleDTO) {
        List<Long> productId = saleDTO.getProduct()
                .stream()
                .map(ProductSoldDTO::getProductId).toList();
        //validate sell id
        productId.forEach(productService::findById);
        List<Product> products = productRepository.findAllById(productId);
        Map<Long, Product> productMap = products
                .stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        //validate stock
        saleDTO.getProduct()
                .forEach(ps -> {
                    Product product = productMap.get(ps.getProductId());
                    if (product.getUnit() < ps.getQuantity()) {
                        throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough"
                                .formatted(product.getName()));
                    }

                });
        Sale sale = saleMapper.toSale(saleDTO);
        saleRepository.save(sale);


        saleDTO.getProduct().forEach(ps -> {
            Product pro = productMap.get(ps.getProductId());
            SaleDetail saleDetail = saleMapper.toSaleDetail(ps);
            saleDetail.setProduct(pro);
            saleDetail.setSale(sale);
            BigDecimal amountProduct = pro.getSalePrice().multiply(BigDecimal.valueOf(ps.getQuantity()));
            saleDetail.setAmount(amountProduct);
            saleDetailRepository.save(saleDetail);


            Integer availableStock = pro.getUnit() - ps.getQuantity();
            pro.setUnit(availableStock);
            productRepository.save(pro);

        });
    }
}
