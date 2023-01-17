package com.example.authenpaymentservice.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class StringUtils {

  public static final String BLANK_STRING_VALUE = "";
  public static final String NULL_STRING_VALUE = "null";

  public static boolean isStringNullOrEmpty(Object obj1) {
    return obj1 == null || BLANK_STRING_VALUE.equals(obj1.toString().trim()) || NULL_STRING_VALUE.equals(obj1.toString().trim());
  }

  public static boolean isNotNullOrEmpty(String obj1) {
    return obj1 != null && !BLANK_STRING_VALUE.equals(obj1.trim()) && !NULL_STRING_VALUE.equals(obj1);
  }

  public static List<?> stringToArray(Class<?> persistentClass, String value, String character) {
    List<String> listString = new ArrayList<>(Arrays.asList(value.split(character)));
    if (persistentClass.equals(String.class)) {
      return listString;
    }
    if (persistentClass.equals(Long.class)) {
      List<Long> listLong = new ArrayList<>();
      for (String string : listString) {
        listLong.add(Long.parseLong(string));
      }
      return listLong;
    }
    return listString;
  }

  public static boolean isInteger(String str) {
    if (str == null || !str.matches("[0-9]+$")) {
      return false;
    }
    return true;
  }

  public static boolean isLong(String str) {
    try {
      Long.valueOf(str);
      return true;
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return false;
    }
  }

  public static String convertLowerParamContains(String value) {
    String result = value.trim().toLowerCase()
        .replace("\\", "\\\\")
        .replaceAll("%", "\\\\%")
        .replaceAll("_", "\\\\_");
    return "%" + result + "%";
  }

  public static String convertUpperParamContains(String value) {
    String result = value.trim().toUpperCase()
        .replace("\\", "\\\\")
        .replaceAll("%", "\\\\%")
        .replaceAll("_", "\\\\_");
    return "%" + result + "%";
  }

  public static String removeSeparator(String pathInput) {
    String path = pathInput;
    path = path.replace("\\\\", "\\").replace("//", "/");
    path = path.replace("\\/", "/").replace("/\\", "/");
    return path;
  }

  public static boolean validString(Object temp) {
    if (temp == null || "".equals(temp.toString().trim())) {
      return false;
    }
    return true;
  }

  public static String getStringParttern(String input, String pattern) {
    try {
      Pattern patterns = Pattern.compile(pattern);
      Matcher matcher = patterns.matcher(input);
      if (matcher.find()) {
        return matcher.group(0);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return input;
  }

  public static String formatLike(String str) {
    return "%" + str.trim().toLowerCase().replace("\\", "\\\\").replaceAll("%", "\\\\%")
        .replaceAll("_", "\\\\_") + "%";
  }

  public static String formatFunction(String function, String str) {
    return " " + function + "(" + str + ") ";
  }

//  public static String formatDate(Date date) {
////        return " to_date('" + DateTimeUtils.convertDateToString(date, ParamUtils.ddMMyyyy) + "', '" + ParamUtils.ddMMyyyy + "')";
//    return DateTimeUtils.convertDateToString(date, Constants.formatterDateText);
//  }

  public static String formatOrder(String str, String direction) {
    return " NLSSORT(" + str + ",'NLS_SORT=vietnamese') " + direction;
  }

  public static boolean checkMaxlength(Long maxlength, String str) {
    if (str != null && str.trim().length() < maxlength) {
      return true;
    }
    return false;
  }

  public static boolean isLongNullOrEmpty(Long obj1) {
    return (obj1 == null || "0L".equals(obj1));
  }

  public static boolean isDoubleNullOrEmpty(Double obj1) {
    return (obj1 == null || "0D".equals(obj1));
  }

  public static String removeDotVersion(String inp) throws Exception {
    String ret = inp.trim();
    try {
      if (ret != null && ret.contains(".")) {
        ret = ret.substring(0, ret.indexOf(".")) + "."
            + ret.substring(ret.indexOf("."), ret.length()).replace(".", "");
      }
    } catch (Exception e) {
      throw e;
    }
    return ret;
  }

  public static String replaceSpecicalChracterSQL(String str) {
//        return str.trim()
//                .replaceAll("%", "\\\\%")
//                .replaceAll("_", "\\\\_");
//        return str.trim().replace("\\", "\\\\").replaceAll("%", "\\%").replaceAll("_", "\\_");

    return str.trim().replace("\\", "\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
  }

  public static void printLogData(String msg, Object object, Class<?> T) {
    try {
//      InetAddress address = InetAddress.getLocalHost();
      ObjectMapper mapper = new ObjectMapper();
      String json = mapper.writeValueAsString(object);
      log.info("---COME PRINT LOGS---");
      log.info("\n Header Request: \n" +
          "\n Host Address: " +
//      address.getHostAddress() +
          "\n Host Name: " +
//          address.getHostName() +
          "\n Request to " + msg + " : {} \n " + json);

      log.info("printLogData ---" + msg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private static int checkHttpSuccess(int httpCode) {
    if (!HttpStatus.valueOf(httpCode).isError()) {
      return 1;
    } else {
      return 0;
    }
  }

  private static String dateFormatFromMili(long timeMilis) {
    Date date = new Date(timeMilis);
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String dateToString = format.format(date);
    return dateToString;
  }
  // 20210107_HaNV15_Add_End

  // 2021/06/07_HaNV15_Add_Start
  public static String listToString(List<String> list) {
    if (list != null) {
      return list.toString().replace("[", "").replace("]", "").replace(" ", "");
    } else {
      return "";
    }
  }
  // 2021/06/07_HaNV15_Add_End

  // Hungtv77 add validate file
  // false - invalid
  // true - valid
  public static boolean validateFileUpload(String lstFileName, List<String> lstFileNameUpload) {
    boolean check = true;
    try {
      List<String> arrFileName = Arrays.asList(lstFileName.split(","));
      for (String item : lstFileNameUpload) {
        if (!arrFileName.contains(item)) {
          check = false;
        }
      }
    } catch (Exception err) {
      log.error(err.getMessage());
    }
    return check;
  }

  // 2021/07/02_HaNV15_Add_Start

  /**
   * Ex: input = 1 and value = 1 then true else false
   *
   * @param input
   * @param valueCompare
   * @return
   */
  public static boolean convertStringToBoolean(String input, String valueCompare) {
    return valueCompare.equals(input) ? true : false;
  }

  public static String choseStringNotEmpty(String string1, String string2) {
    if (isNotNullOrEmpty(string1)) {
      return string1;
    } else {
      return string2;
    }
  }
  // 2021/07/02_HaNV15_Add_End
}
