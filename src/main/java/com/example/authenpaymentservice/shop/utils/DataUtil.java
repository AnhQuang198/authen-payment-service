package com.example.authenpaymentservice.shop.utils;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.authenpaymentservice.shop.utils.StringUtils.isStringNullOrEmpty;

@Slf4j
public class DataUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    public static List<String> splitDot(String input) {
        return Splitter.on(".").trimResults().omitEmptyStrings().splitToList(input);
    }

    public static List<String> splitCharE(String input) {
        return Splitter.on("E").trimResults().omitEmptyStrings().splitToList(input);
    }

    public static List<String> parseInputList(String input) {
        return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(input);
    }

    public static boolean isNullOrEmpty(String obj1) {
        return (obj1 == null || "".equals(obj1.trim()));
    }

    public static boolean isNumber(String input) {
        try {
            Double doubles = Double.parseDouble(input);
            if (doubles != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("Exception:", e);
            return false;
        }
        return false;
    }

    public static boolean isInteger(String input) {
        try {
            Integer integer = Integer.parseInt(input);
            if (integer != null && integer > 0) {
                return true;
            }
        } catch (Exception e) {
            log.error("Exception:", e);
            return false;
        }
        return false;
    }

    public static boolean isLong(String input) {
        try {
            Long aLong = Long.parseLong(input);
            if (aLong != null && aLong > 0) {
                return true;
            }
        } catch (Exception e) {
            log.error("Exception:", e);
            return false;
        }
        return false;
    }

    private static <T> T copy(T entity) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = entity.getClass();
        T newEntity = (T) entity.getClass().newInstance();

        while (clazz != null) {
            copyFields(entity, newEntity, clazz);
            clazz = clazz.getSuperclass();
        }

        return newEntity;
    }

    private static <T> T copyFields(T entity, T newEntity, Class<?> clazz)
            throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(newEntity, field.get(entity));
        }
        return newEntity;
    }

    public static String commonPaser(SimpleDateFormat format, String src) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = dateFormat.parse(src);
        return format.format(date);
    }

    public static Date convertStringToDate(String date1) throws Exception {
        String date = date1;
        String pattern = "dd/MM/yyyy HH:mm:ss";
        if (date.length() <= 10) {
            date = date + " 00:00:00";
        }
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToTime(String date, String pattern) throws ParseException {
        if (date == null || "".equals(date.trim())) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);

    }

    public static Timestamp date2Timestamp(Date value) {
        if (value != null) {
            return new Timestamp(value.getTime());
        }
        return null;
    }

    public static Timestamp string2Timestamp(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date value = sdf.parse(date);

        if (value != null) {
            return new Timestamp(value.getTime());
        }
        return null;
    }

    public static Date ddMMyyyyToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(str);
        return date;
    }

    public static String date2ddMMyyyyHHMMss(Date value) {
        if (value != null) {
            SimpleDateFormat dateTimeNoSlash = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return dateTimeNoSlash.format(value);
        }
        return "";
    }

    public static Boolean forceAlphaNumericOnly(String str) {
        return str.matches("[a-zA-Z0-9]*");
    }

    public static Map<String, Object> getSqlLanguageExchange(String appliedSystem,
                                                             String appliedBussiness, String leeLocale) {

        Map<String, Object> result = new HashMap<String, Object>();
        String sql = "SELECT a.lee_id leeId, a.applied_system appliedSystem,"
                + " a.applied_bussiness appliedBussiness, a.bussiness_id bussinessId,"
                + " a.bussiness_code bussinessCode, a.lee_locale leeLocale,"
                + " a.lee_value leeValue FROM common_gnoc.language_exchange a "
                + " where a.applied_system = :appliedSystem and a.applied_bussiness = :appliedBussiness "
                + " and lower(a.lee_locale) like :leeLocale";
        result.put("sql", sql);
        Map mapParam = new HashMap();
        mapParam.put("appliedSystem", appliedSystem);
        mapParam.put("appliedBussiness", appliedBussiness);
        mapParam.put("leeLocale", "" + leeLocale.toLowerCase() + "%");

        Map mapType = new HashMap();
        mapType.put("appliedSystem", "Long");
        mapType.put("appliedBussiness", "Long");
        mapType.put("leeLocale", "String");

        result.put("mapParam", mapParam);
        result.put("mapType", mapType);
        return result;
    }

    public static Map<String, Object> getSqlLanguageExchange(String appliedSystem,
                                                             String appliedBussiness, String appliedBussinessColumn, String leeLocale) {
        Map<String, Object> result = new HashMap<>();
        String sql = "SELECT a.lee_id leeId, a.applied_system appliedSystem,"
                + " a.applied_bussiness appliedBussiness, a.bussiness_id bussinessId,"
                + " a.bussiness_code bussinessCode, a.lee_locale leeLocale,"
                + " a.lee_value leeValue FROM common_gnoc.language_exchange a "
                + " where a.applied_system = :appliedSystem and a.applied_bussiness = :appliedBussiness "
                + " and a.lee_locale = :leeLocale and a.bussiness_code = :appliedBussinessColumn";
        result.put("sql", sql);
        Map mapParam = new HashMap();
        mapParam.put("appliedSystem", appliedSystem);
        mapParam.put("appliedBussiness", appliedBussiness);
        mapParam.put("appliedBussinessColumn", appliedBussinessColumn);
        mapParam.put("leeLocale", leeLocale);

        Map mapType = new HashMap();
        mapType.put("appliedSystem", "Long");
        mapType.put("appliedBussiness", "Long");
        mapType.put("appliedBussinessColumn", "Long");
        mapType.put("leeLocale", "String");

        result.put("mapParam", mapParam);
        result.put("mapType", mapType);
        return result;
    }

    public static List<String> splitListFile(String strFiles) {
        List<String> lstFile = new ArrayList<>();
        if (!isStringNullOrEmpty(strFiles)) {
            String lst[] = strFiles.split(";");
            lstFile = Arrays.asList(lst);
        }
        return lstFile;
    }

    public static List<String> splitListFileByComma(String strFiles) {
        List<String> lstFile = new ArrayList<>();
        if (!isStringNullOrEmpty(strFiles)) {
            String lst[] = strFiles.split(",");
            lstFile = Arrays.asList(lst);
        }
        return lstFile;
    }

    public static List<?> subPageList(List<?> lst, int page, int pageSize) {
        if (lst == null) {
            return null;
        }
        int start = (page - 1) * pageSize;
        int end = start + pageSize;
        if (start < 0) {
            start = 0;
        }
        if (start > end) {
            return new ArrayList<>();
        }
        if (start > (lst.size() - 1)) {
            return new ArrayList<>();
        }
        if (end > lst.size()) {
            end = lst.size();
        }
        return lst.subList(start, end);
    }

    public static List<?> subPageListFromTo(List<?> lst, int start, int end) {
        if (lst == null) {
            return null;
        }
        if (start < 0) {
            start = 0;
        }
        if (start > end) {
            return new ArrayList<>();
        }
        if (start > (lst.size() - 1)) {
            return new ArrayList<>();
        }
        if (end > lst.size()) {
            end = lst.size();
        }
        return lst.subList(start, end);
    }

    public static boolean validateIP(String ip) {
        try {
            String IPADDRESS_PATTERN
                    = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
            Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return true;
        }
    }

    public static String validateDateTimeDdMmYyyy_HhMmSs(String input) {
        try {
            String[] data = input.split("\\s+");
            String[] hhmmss = data[1].split(":");
            if (hhmmss[0] != null && hhmmss[1] != null && hhmmss[2] != null) {
                int hh = Integer.parseInt(hhmmss[0]);
                int mm = Integer.parseInt(hhmmss[1]);
                int ss = Integer.parseInt(hhmmss[2]);
                if (hh > 23 || mm > 59 || ss > 59) {
                    return "InvalidFormat";
                }
            }
            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(data[0], dft);
            return "";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "InvalidFormat";
        }
    }

    public static String validateDateTimeDdMmYyyy(String input) {
        try {
            String[] data = input.split("\\s+");
            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(data[0], dft);
            return "";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "InvalidFormat";
        }
    }

    public static Date getDate(String string, String format) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            return sdf.parse(string);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static Boolean isBoolean(String value) {
        return "1".equals(value);
    }
}
