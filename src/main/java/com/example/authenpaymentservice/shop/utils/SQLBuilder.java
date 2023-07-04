package com.example.authenpaymentservice.shop.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class SQLBuilder {
    public static final String SHOP = "shop";

    public enum SHOP {
        GET_LIST_DATA(SHOP, "get-list-shop");
        private String module;
        private String queryId;

        SHOP(String module, String queryId) {
            this.module = module;
            this.queryId = queryId;
        }
        public String getModule() {
            return module;
        }
        public void setModule(String module) {
            this.module = module;
        }
        public String getQueryId() {
            return queryId;
        }
        public void setQueryId(String queryId) {
            this.queryId = queryId;
        }
    }

    public static String getSqlQueryById(String module, String queryId) {
        InputStream inputStream = null;
        try {
            String filePath = "sql" + File.separator + module + File.separator + queryId + ".sql";
            log.info("SQL file path:" + filePath);
            Resource resource = new ClassPathResource(filePath);
            inputStream = resource.getInputStream();
            if (inputStream != null) {
                return new String(String.valueOf(inputStream.read()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }
}
