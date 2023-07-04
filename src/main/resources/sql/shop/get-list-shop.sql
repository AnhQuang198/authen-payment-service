SELECT
    s.id as shopId,
    s.shop_name as shopName,
    s.state as state,
    s.is_locked as isLocked,
    s.avatar_url as avatarUrl,
    s.cover_url as coverUrl,
    s.description as description,
    s.created_at as createdAt,
    u.email as email,
    u.phone as phone,
    sadd.add_detail as addDetail,
    c.id as cityId,
    c.city_name as cityName,
    w.id as wardId,
    w.ward_name as wardName,
    d.id as districtId,
    d.district_name as districtName
FROM shop s
         INNER JOIN user u ON s.id = u.id
         LEFT JOIN shop_address sadd ON s.id = sadd.shop_id
         LEFT JOIN city c ON sadd.city_id = c.id
         LEFT JOIN ward w ON sadd.ward_id = w.id
         LEFT JOIN district d ON sadd.district_id = d.id WHERE 1=1
