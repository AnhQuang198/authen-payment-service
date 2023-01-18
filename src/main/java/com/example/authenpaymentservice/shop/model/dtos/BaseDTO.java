package com.example.authenpaymentservice.shop.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDTO {
    @JsonIgnore
    private Integer totalRow;
}
