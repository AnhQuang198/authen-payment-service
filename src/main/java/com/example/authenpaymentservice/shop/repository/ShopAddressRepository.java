package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.entity.ShopAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopAddressRepository extends JpaRepository<ShopAddress, Long> {
    ShopAddress findByIdAndShopId(long id, long shopId);
}
