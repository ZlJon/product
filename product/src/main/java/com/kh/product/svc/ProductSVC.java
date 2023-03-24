package com.kh.product.svc;


import com.kh.product.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC { //컨트롤러와 DAO 브릿지 역할
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
}
