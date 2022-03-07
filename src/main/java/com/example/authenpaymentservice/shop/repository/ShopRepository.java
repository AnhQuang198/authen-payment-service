package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.enums.ShopState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop findShopById(int id);

    @Query("SELECT new com.example.authenpaymentservice.shop.dtos.ShopDTO(s.id, s.name, u.email, u.phone, s.state, s.isLocked, s.createdAt)" +
            " FROM Shop s LEFT JOIN User u ON s.id = u.id WHERE s.state = ?1")
    Page<ShopDTO> getShops(ShopState shopState, Pageable pageable);

    @Query("SELECT new com.example.authenpaymentservice.shop.dtos.ShopDTO(s.id, s.name, u.email, u.phone, s.state, s.isLocked, s.createdAt)" +
            " FROM Shop s LEFT JOIN User u ON s.id = u.id WHERE s.state = ?1 AND s.name LIKE ?2%")
    Page<ShopDTO> getShopsByName(ShopState shopState, String shopName, Pageable pageable);
}
