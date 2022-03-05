package com.example.authenpaymentservice.shop.model.response;

import com.example.authenpaymentservice.shop.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.model.response.data.Metadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponse {
    private List<ShopDTO> shops;
    private Metadata metadata;
}
