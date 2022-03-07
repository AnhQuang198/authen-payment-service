package com.example.authenpaymentservice.shop.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ObjectResponse<T> {
    private List<T> data;
}
