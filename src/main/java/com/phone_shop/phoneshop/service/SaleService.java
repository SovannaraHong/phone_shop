package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.entity.Sale;

public interface SaleService {

    void sell(SaleDTO saleDTO);

    Sale getById(Long id);

    void cancelSale(Long id);

}
