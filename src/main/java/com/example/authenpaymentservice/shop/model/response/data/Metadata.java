package com.example.authenpaymentservice.shop.model.response.data;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class Metadata {
  private final int page;
  private final int size;
  private final long total;
  private final long totalPages;

  public static Metadata of(Page page) {
    return new Metadata(
            page.getNumber(), page.getContent().size(), page.getTotalElements(), page.getTotalPages());
  }
}