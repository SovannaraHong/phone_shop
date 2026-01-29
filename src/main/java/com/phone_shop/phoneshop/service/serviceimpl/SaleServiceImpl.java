package com.phone_shop.phoneshop.service.serviceimpl;


import com.phone_shop.phoneshop.dto.ProductSoldDTO;
import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.Sale;
import com.phone_shop.phoneshop.entity.SaleDetail;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;


    @Override
    public void sell(SaleDTO saleDTO) {
        //validate id product
        List<Long> productId = saleDTO.getProduct().stream().map(ProductSoldDTO::getProductId).toList();
        productId.forEach(productService::findById);
        List<Product> products = productRepository.findAllById(productId);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        //validate stock
        //check stock hav enough or not
        saleDTO.getProduct().forEach(ps -> {
            Product product = productMap.get(ps.getProductId());
            if (product.getSalePrice() == null) {
                throw new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "Product [%s] does not have a selling price yet".formatted(product.getName())
                );
            }
            if (product.getUnit() < ps.getQuantity()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "product [%s] is not enough".formatted(product.getName()));
            }

        });

        Sale sale = saleMapper.toSale(saleDTO);
        sale.setIsActive(true);
        saleRepository.save(sale);

        saleDTO.getProduct().forEach(ps -> {
            Product product = productMap.get(ps.getProductId());
            SaleDetail saleDetail = saleMapper.toSaleDetail(ps);
            saleDetail.setSale(sale);
            saleDetail.setProduct(product);
            BigDecimal amount = product.getSalePrice().multiply(BigDecimal.valueOf(ps.getQuantity()));
            saleDetail.setAmount(amount);
            saleDetailRepository.save(saleDetail);
            saleDetail.setUnitSale(ps.getQuantity());
            Integer availableStock = product.getUnit() - ps.getQuantity();
            product.setUnit(availableStock);
            productRepository.save(product);
        });

    }

    @Override
    public Sale getById(Long id) {
        Optional<Sale> saleId = saleRepository.findById(id);
        return saleId.orElseThrow(() -> new ResourceNotFoundException("sale", id, "id"));
    }

    @Override
    public void cancelSale(Long id) {
        Sale sale = getById(id);
        if (!sale.getIsActive()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Sale id =[%s] already cancel".formatted(id));
        }
        sale.setIsActive(false);
        saleRepository.save(sale);

        List<SaleDetail> saleDetailId = saleDetailRepository.findBySaleId(id);
        List<Long> productId = saleDetailId
                .stream()
                .map(sd -> sd.getProduct().getId()).toList();
        List<Product> Products = productRepository.findAllById(productId);
        Map<Long, Product> productMap = Products
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        saleDetailId.forEach(sd -> {
            Product product = productMap.get(sd.getProduct().getId());
            product.setUnit(product.getUnit() + sd.getUnitSale());
            productRepository.save(product);
        });

    }


}

