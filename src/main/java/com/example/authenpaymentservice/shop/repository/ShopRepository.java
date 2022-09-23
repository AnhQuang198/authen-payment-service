package com.example.authenpaymentservice.shop.repository;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.example.authenpaymentservice.shop.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.enums.ShopState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepository {

    //    Shop findShopById(int id);
//
//    @Query("SELECT new com.example.authenpaymentservice.shop.dtos.ShopDTO(s.id, s.name, u.email, u.phone, s.state, s.isLocked, " +
//            "s.createdAt)" +
//            " FROM Shop s " +
//            "LEFT JOIN User u ON s.id = u.id " +
//            "LEFT JOIN ShopAddress a ON s.id = a.shopId" +
//            " WHERE s.state = ?1")
//    Page<ShopDTO> getShops(ShopState shopState, Pageable pageable);
//
//    @Query("SELECT new com.example.authenpaymentservice.shop.dtos.ShopDTO(s.id, s.name, u.email, u.phone, s.state, s.isLocked, " +
//            "s.createdAt)" +
//            " FROM Shop s " +
//            "LEFT JOIN User u ON s.id = u.id " +
//            "LEFT JOIN ShopAddress a ON s.id = a.shopId " +
//            "WHERE s.state = ?1 AND s.name LIKE ?2%")
//    Page<ShopDTO> getShopsByName(ShopState shopState, String shopName, Pageable pageable);

    private DataRepository dataRepository;

    @Autowired
    public ShopRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void save(Shop shop) {
        dataRepository.save(shop);
    }

    public void saveOrUpdate(Shop shop) {
        dataRepository.saveOrUpdate(shop);
    }

    public Page<ShopDTO> getShops(String shopState, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT id, shop_name AS shopName, state, is_locked AS isLocked, created_at AS createdAt")
                .append("FROM shop")
                .append("WHERE state = ").append(shopState);

        return dataRepository.listAll(Shop.class, pageable);
    }
}
