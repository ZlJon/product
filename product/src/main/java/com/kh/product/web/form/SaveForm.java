package com.kh.product.web.form;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SaveForm {

  @NotBlank
  private String pname;

  @Positive
  @NotNull
  private Long quantity;

  @Positive
  @NotNull
  @Min(1000)
  private Long price;
}
