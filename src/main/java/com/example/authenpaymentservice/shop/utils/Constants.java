package com.example.authenpaymentservice.shop.utils;

public class Constants {
    public static final String TYPE_NUMBER = "LONG,INTEGER,SHORT,BYTE,INT,DOUBLE,FLOAT";
    public static final String TYPE_DATE = "DATE";

    public interface RESULT {

        String EXIST = "EXIST";
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String DUPLICATE = "DUPLICATE";
        String FILE_IS_NULL = "FILE_IS_NULL";
        String FILE_NOT_FOUND = "FILE_NOT_FOUND";
        String NODATA = "NODATA";
        String FILE_ERROR = "FILE_ERROR";
        String FILE_INVALID = "FILE_INVALID";
        String FILE_TYPE_INVALID = "FILE_TYPE_INVALID";
        String DATA_OVER = "DATA_OVER";
        String NOT_ACCESS = "NOT_ACCESS";
        String NO_CAN_DELETE = "NO_CAN_DELETE";
        String FILE_INVALID_FORMAT = "FILE_INVALID_FORMAT";
        String FAIL = "FAIL";
        String ERROR_SYSTEM = "ERROR_SYSTEM";
        String SUCCESS_NOT_WO = "SUCCESS_NOT_WO";
        String DELETE_PROCESS = "FAIL: NULL PROCESS_ID OR TABLE_NAME";
        String DELETE_ATTACH_FILE = "FAIL: TEM_IMPORT_ID NOT NULL";
        String DELETE_REQUIRE_EXIST = "DELETE_REQUIRE_EXIST";
        String ERROR_UPDATE = "ERROR_UPDATE";
        String FILE_INVALID_FORM = "FILE_INVALID_FORM";
    }
}
