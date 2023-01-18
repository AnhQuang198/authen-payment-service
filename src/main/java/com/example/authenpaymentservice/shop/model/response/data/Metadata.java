package com.example.authenpaymentservice.shop.model.response.data;

import com.example.authenpaymentservice.shop.model.Datatable;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class Metadata {
  private final int page;
  private final int size;
  private final long total;
  private final long totalPages;

  public static Metadata of(Datatable data) {
    return new Metadata(
            data.getPage(), data.getSize(), data.getTotalElements(), data.getTotalPages());
  }
}
