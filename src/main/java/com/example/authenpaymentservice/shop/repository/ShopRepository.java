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
        sql.append("        s.created_at as createdAt, ");
        sql.append("        u.email as email, ");
        sql.append("        u.phone as phone, ");
        sql.append("        sadd.add_detail as addDetail, ");
        sql.append("        c.id as cityId, ");
        sql.append("        c.city_name as cityName, ");
        sql.append("        w.id as wardId, ");
        sql.append("        w.ward_name as wardName, ");
        sql.append("        d.id as districtId, ");
        sql.append("        d.district_name as districtName ");
        sql.append("        FROM shop s ");
        sql.append("        INNER JOIN user u ON s.id = u.id ");
        sql.append("        LEFT JOIN shop_address sadd ON s.id = sadd.shop_id ");
        sql.append("        LEFT JOIN city c ON sadd.city_id = c.id ");
        sql.append("        LEFT JOIN ward w ON sadd.ward_id = w.id ");
        sql.append("        LEFT JOIN district d ON sadd.district_id = d.id WHERE 1=1 ");
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
        return getListDataTableBySqlQuery(sql.toString(), params, request.getPageIndex(), request.getPageSize(), ShopDTO.class, "shopId", "desc");
    }

}
