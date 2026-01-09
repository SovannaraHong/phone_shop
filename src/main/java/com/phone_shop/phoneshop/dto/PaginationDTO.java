package com.phone_shop.phoneshop.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO {
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPage;
    boolean last;
    boolean first;
    boolean empty;
    int numberOfElements;

    
}
