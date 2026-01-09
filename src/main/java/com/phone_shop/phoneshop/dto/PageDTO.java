package com.phone_shop.phoneshop.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO {
    private List<?> list;
    private PaginationDTO paginationDTO;

    public PageDTO(Page<?> page) {
        this.list = page.getContent();
        this.paginationDTO = PaginationDTO.builder()
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .numberOfElements(page.getNumberOfElements())
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();


    }

}
