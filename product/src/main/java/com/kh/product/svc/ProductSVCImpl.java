package com.kh.product.svc;

import com.kh.product.dao.Product;
import com.kh.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {

  private final ProductDAO productDAO;
  /** 저장
   * @param product
   * @return
   */
  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  /** 조회
   * @param pid
   * @return
   */
  @Override
  public Optional<Product> findId(Long pid) {
    return productDAO.findId(pid);
  }

  /** 수정
   * @param pid
   * @param product
   * @return
   */
  @Override
  public int update(Long pid, Product product) {
    return productDAO.update(pid, product);
  }

  /** 삭제
   * @param pid
   * @return
   */
  @Override
  public int delete(Long pid) {
    return productDAO.delete(pid);
  }

  /** 목록
   * @return
   */
  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
}
