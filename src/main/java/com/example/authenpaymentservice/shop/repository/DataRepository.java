//package com.example.authenpaymentservice.shop.repository;
//
//import lombok.extern.log4j.Log4j2;
//import org.hibernate.Query;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.transform.Transformers;
//import org.hibernate.type.*;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.query.QueryUtils;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Log4j2
//@Component
//public class DataRepository {
//    @PersistenceContext
//    private EntityManager entityManager;
//
////    @Autowired
////    public DataRepository(EntityManager entityManager) {
////        this.entityManager = entityManager;
////    }
//
//    public SessionFactory getSessionFactory() {
//        return entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
//    }
//
//    public Session getSession() {
//        return entityManager.unwrap(Session.class);
//    }
//
//    public void flushSession() {
//        getSession().flush();
//    }
//
//    public void clear() {
//        getSession().clear();
//    }
//
//    public Query createQuery(String hql) {
//        return getSession().createQuery(hql);
//    }
//
//    public SQLQuery createSQLQuery(String sql) {
//        return getSession().createSQLQuery(sql);
//    }
//
//    public void save(Object dataObject) {
//        getSession().save(dataObject);
//    }
//
//    public void saveOrUpdate(Object dataObject) {
//        getSession().saveOrUpdate(dataObject);
//    }
//
//    public void delete(Object dataObject) {
//        getSession().delete(dataObject);
//    }
//
//    public void deleteByIds(List<Long> arrId, Class className, String idColumn) {
//        if ((arrId != null) && !arrId.isEmpty()) {
//            String hql = "DELETE FROM " + className.getName() + " t WHERE :idColumn" + " IN (:arrId) ";
//            Query query = getSession().createQuery(hql);
//            query.setParameter("idColumn", "t." + idColumn);
//            query.setParameterList("arrId", arrId);
//            query.executeUpdate();
//        }
//    }
//
//    public void deleteById(Long id, Class className, String idColumn) {
//        String hql = "DELETE FROM " + className.getName() + " t WHERE t." + idColumn + " = ? ";
//        Query query = getSession().createQuery(hql);
//        query.setParameter(0, id);
//        query.executeUpdate();
//    }
//
//    public <T> T get(String nativeQuery, List<Object> paramList, Class obj) {
//        SQLQuery query = createSQLQuery(nativeQuery);
//        setResultTransformer(query, obj);
//
//        if (paramList != null || !paramList.isEmpty()) {
//            int paramSize = paramList.size();
//            for (int i = 0; i < paramSize; i++) {
//                query.setParameter(i, paramList.get(i));
//            }
//        }
//        query.setMaxResults(1);
//        return (T) query.uniqueResult();
//    }
//
//    public <T> List<T> getAll(Class<T> tableName, String orderColumn) {
//        String hql = " FROM " + tableName.getName() + " t ORDER BY " + "t." + orderColumn;
//        Query query = getSession().createQuery(hql);
//        return query.list();
//    }
//
//    public <T> List<T> findByIds(Class<T> tableName, String idColumn, String ids, String orderColumn) {
//        StringBuilder hql = new StringBuilder(" FROM " + tableName.getName() + " t ");
//        if (!ids.isEmpty()) {
//            hql.append(" WHERE t.").append(idColumn).append(" IN ( ").append(ids).append(" ) ");
//        }
//        hql.append(" ORDER BY ").append("t.").append(orderColumn);
//        Query query = getSession().createQuery(hql.toString());
//        return query.list();
//    }
//
//    public <T> Page<T> listAll(Class dataModel, Pageable pageable) {
//        // TODO Auto-generated method stub
//        String hql = " FROM " + dataModel.getName() + " t";
//        String countHsql = "SELECT count(*) ";
//        hql = QueryUtils.applySorting(hql, pageable.getSort());
//        Query objectQuery = getSession().createQuery(hql);
//        Query countQuery = getSession().createQuery(countHsql + hql);
//        objectQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
//        objectQuery.setMaxResults(pageable.getPageSize());
//        return new PageImpl<T>(objectQuery.list(), pageable, (long) countQuery.uniqueResult());
//    }
//
//    public void setResultTransformer(SQLQuery query, Class obj) {
//        Field[] fileds = obj.getDeclaredFields();
//        Map<String, String> mapFileds = new HashMap();
//        for (Field filed : fileds) {
//            mapFileds.put(filed.getName(), filed.getGenericType().toString());
//        }
//        List<String> aliasColumns = getReturnAliasColumns(query);
//        for (String aliasColumn : aliasColumns) {
//            String dataType = mapFileds.get(aliasColumn);
//            if (dataType == null) {
//                log.debug(aliasColumn + " is not defined");
//            } else {
//                Type hbmType = null;
//                if ("class java.lang.Long".equals(dataType)) {
//                    hbmType = LongType.INSTANCE;
//                } else if ("class java.lang.Integer".equals(dataType)) {
//                    hbmType = IntegerType.INSTANCE;
//                } else if ("class java.lang.Double".equals(dataType)) {
//                    hbmType = DoubleType.INSTANCE;
//                } else if ("class java.lang.String".equals(dataType)) {
//                    hbmType = StringType.INSTANCE;
//                } else if ("class java.lang.Boolean".equals(dataType)) {
//                    hbmType = BooleanType.INSTANCE;
//                } else if ("class java.util.Date".equals(dataType)) {
//                    hbmType = TimestampType.INSTANCE;
//                } else if ("class java.math.BigDecimal".equals(dataType)) {
//                    hbmType = BigDecimalType.INSTANCE;
//                }
//                if (hbmType == null) {
//                    log.debug(dataType + " is not supported");
//                } else {
//                    query.addScalar(aliasColumn, hbmType);
//                }
//            }
//        }
//        query.setResultTransformer(Transformers.aliasToBean(obj));
//    }
//
//    public List<String> getReturnAliasColumns(SQLQuery query) {
//        List<String> aliasColumns = new ArrayList();
//        String sqlQuery = query.getQueryString();
//        sqlQuery = sqlQuery.replace("\n", " ");
//        sqlQuery = sqlQuery.replace("\t", " ");
//        int numOfRightPythis = 0;
//        int startPythis = -1;
//        int endPythis = 0;
//        boolean hasRightPythis = true;
//        while (hasRightPythis) {
//            char[] arrStr = sqlQuery.toCharArray();
//            hasRightPythis = false;
//            int idx = 0;
//            for (char c : arrStr) {
//                if (idx > startPythis) {
//                    if ("(".equalsIgnoreCase(String.valueOf(c))) {
//                        if (numOfRightPythis == 0) {
//                            startPythis = idx;
//                        }
//                        numOfRightPythis++;
//                    } else if (")".equalsIgnoreCase(String.valueOf(c))) {
//                        if (numOfRightPythis > 0) {
//                            numOfRightPythis--;
//                            if (numOfRightPythis == 0) {
//                                endPythis = idx;
//                                break;
//                            }
//                        }
//                    }
//                }
//                idx++;
//            }
//            if (endPythis > 0) {
//                sqlQuery = sqlQuery.substring(0, startPythis) + " # " + sqlQuery.substring(endPythis + 1);
//                hasRightPythis = true;
//                endPythis = 0;
//            }
//        }
//        String arrStr[] = sqlQuery.substring(0, sqlQuery.toUpperCase().indexOf(" FROM ")).split(",");
//        for (String str : arrStr) {
//            String[] temp = str.trim().split(" ");
//            String alias = temp[temp.length - 1].trim();
//            if (alias.contains(".")) {
//                alias = alias.substring(alias.lastIndexOf(".") + 1).trim();
//            }
//            if (alias.contains(",")) {
//                alias = alias.substring(alias.lastIndexOf(",") + 1).trim();
//            }
//            if (alias.contains("`")) {
//                alias = alias.replace("`", "");
//            }
//            if (!aliasColumns.contains(alias)) {
//                aliasColumns.add(alias);
//            }
//        }
//        return aliasColumns;
//    }
//
//    public Object mapDataQuery(Query query, Class obj) {
//        List<Object> list = query.list();
//        for (Object o : list) {
//            BeanUtils.copyProperties(o, obj);
//        }
//        return obj;
//    }
//}
