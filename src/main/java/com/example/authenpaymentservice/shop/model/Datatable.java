package com.example.authenpaymentservice.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Datatable {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private List<?> data;
}
