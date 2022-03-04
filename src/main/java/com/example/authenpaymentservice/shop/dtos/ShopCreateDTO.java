package com.example.authenpaymentservice.shop.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ShopCreateDTO {
    @NotNull
    @Size(min = 6, max = 50)
    private String name;

    @NotNull
    private String avatarUrl;

    @NotNull
    private String coverUrl;

    private String description;
}
