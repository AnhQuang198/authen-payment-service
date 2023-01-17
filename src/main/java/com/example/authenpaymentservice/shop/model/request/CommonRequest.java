package com.example.authenpaymentservice.shop.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class CommonRequest {
  private int pageIndex;
  private int pageSize;
  private Map<String, String> sortValues;
  private Map<String, String> filterValues;
}
