package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.dtos.ShopStatusDTO;
import com.example.authenpaymentservice.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("SELECT new com.example.authenpaymentservice.shop.dtos.ShopStatusDTO(s.id, s.isLocked, s.state) FROM Shop as s WHERE s.id = ?1")
    ShopStatusDTO getShopStatus(int userId);


}
