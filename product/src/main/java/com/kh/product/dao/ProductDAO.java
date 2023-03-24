package com.kh.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

  //저장
  Long save(Product product);

  //조회
  Optional<Product> findId(Long pid);

  //수정
  int update(Long pid, Product product);

  //삭제
  int delete(Long pid);

  //목록
  List<Product> findAll();

  //상품 존재 유무
  boolean isExist(Long productId);

  //등록된 상품 수
  int countOfRecord();
}
