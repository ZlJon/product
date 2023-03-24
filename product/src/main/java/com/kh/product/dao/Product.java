package com.kh.product.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //디폴트 생성자
@AllArgsConstructor //모든 멤버필드를 매개변수로 하는 생성자 생성하는 어노테이션
public class Product {

  private Long pid;

  private String pname;

  private Long quantity;

  private Long price;
}
