package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.model.Datatable;
import com.example.authenpaymentservice.shop.model.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.model.request.CommonRequest;
import com.example.authenpaymentservice.shop.utils.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ShopRepository extends BaseRepository{

    public Datatable getShops(CommonRequest request) {
        if (request.getFilterValues() == null) {
            request.setFilterValues(new HashMap<>());
        }
        Map<String, String> filterValues = request.getFilterValues();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("        s.id as shopId, ");
        sql.append("        s.shop_name as shopName, ");
        sql.append("        s.state as state, ");
        sql.append("        s.is_locked as isLocked, ");
        sql.append("        s.avatar_url as avatarUrl, ");
        sql.append("        s.cover_url as coverUrl, ");
        sql.append("        s.description as description, ");
        sql.append("        s.created_at as createdAt ");
        sql.append("        FROM shop s WHERE 1=1 ");
        //shopName
        if (StringUtils.isNotNullOrEmpty(filterValues.get("name"))) {
            sql.append("AND s.name = :name ");
            params.put("name", filterValues.get("name"));
        }
        //state
        if (StringUtils.isNotNullOrEmpty(filterValues.get("state"))) {
            sql.append("AND s.state = :state ");
            params.put("state", filterValues.get("state").toUpperCase());
        }
        return getListDataTableBySqlQuery(sql.toString(), params, request.getPageIndex(), request.getPageSize(), ShopDTO.class, "createdAt", "desc");
    }

}
