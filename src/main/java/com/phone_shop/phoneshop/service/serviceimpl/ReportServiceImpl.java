package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ProductReportDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.SaleDetail;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.repository.SaleDetailRepository;
import com.phone_shop.phoneshop.service.ReportService;
import com.phone_shop.phoneshop.specification.SaleDetailFilter;
import com.phone_shop.phoneshop.specification.SaleDetailSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductReportDTO> productReportSold(LocalDateTime startDate, LocalDateTime endDate) {
        List<ProductReportDTO> lists = new ArrayList<>();
        SaleDetailFilter saleDetailFilter = new SaleDetailFilter();
        saleDetailFilter.setStartDate(startDate);
        saleDetailFilter.setEndDate(endDate);
        SaleDetailSpec spec = new SaleDetailSpec(saleDetailFilter);

        //design report
        List<SaleDetail> saleDetails = saleDetailRepository.findAll(spec);

        List<Long> productIds = saleDetails.stream().map(sd -> sd.getProduct().getId()).toList();
        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products
                .stream()
                .collect(Collectors
                        .toMap(Product::getId, Function.identity()));
        Map<Product, List<SaleDetail>> saleDetailMap = saleDetails
                .stream()
                .collect(Collectors.groupingBy(SaleDetail::getProduct));

        for (var entry : saleDetailMap.entrySet()) {


            Product product = productMap.get(entry.getKey().getId());
            ProductReportDTO productReportDTO = new ProductReportDTO();
            List<SaleDetail> sdList = entry.getValue();
            Integer unit = sdList.stream().map(SaleDetail::getUnitSale).reduce(0, Integer::sum);
            double amount = sdList.stream().mapToDouble(sd -> sd.getUnitSale() * sd.getAmount().doubleValue()).sum();
            productReportDTO.setProductId(product.getId());
            productReportDTO.setProductName(product.getName());
            productReportDTO.setProductUnit(unit);
            productReportDTO.setTotalAmount(BigDecimal.valueOf(amount));
            lists.add(productReportDTO);
        }

        return lists;
    }
}
