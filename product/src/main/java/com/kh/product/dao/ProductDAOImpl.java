package com.kh.product.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{

  private final NamedParameterJdbcTemplate template;

  /** 저장
   * @param product
   * @return
   */
  @Override
  public Long save(Product product) {
    StringBuffer stringBuffer = new StringBuffer(); //쿼리문 활용하기
    stringBuffer.append(" insert into product(pid, pname, quantity, price) "); //데이터 베이스에 데이터 넣기
    stringBuffer.append(" values(product_pid_seq.nextval, :pname, :quantity, :price ) "); //시퀸스 등록

    SqlParameterSource parameter = new BeanPropertySqlParameterSource(product);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(stringBuffer.toString(), parameter, keyHolder, new String[]{"pid"});

    long pid = keyHolder.getKey().longValue();
    return pid;
  }

  /** 조회
   * @param pid
   * @return
   */
  @Override
  public Optional<Product> findId(Long pid) { //옵셔널=> 아무 타입 객체 하나 넣을 수 있음
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" select pid, pname, quantity, price ");
    stringBuffer.append(" from product ");
    stringBuffer.append(" where pid = :pid ");
    try {
      Map<String, Long> parameter = Map.of("pid", pid); //map=> 키 id : 벨류 pid 선언
      Product product = template.queryForObject(stringBuffer.toString(), parameter, productRowMapper());

      return Optional.of(product);
    } catch (EmptyResultDataAccessException e) {

      return Optional.empty();
    }
  }

  // 맵핑 -> 수동 맵핑
  private RowMapper<Product> productRowMapper() {
    return (rs, rowNum) -> {
      Product product = new Product();
      product.setPid(rs.getLong("pid"));
      product.setPname(rs.getString("pname"));
      product.setQuantity(rs.getLong("quantity"));
      product.setPrice(rs.getLong("price"));
      return product;
    };
  }

  /** 수정
   * @param pid
   * @param product
   * @return
   */
  @Override
  public int update(Long pid, Product product) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" update product ");
    stringBuffer.append(" set pname = :pname, ");
    stringBuffer.append(" quantity = :quantity, ");
    stringBuffer.append(" price = :price ");
    stringBuffer.append(" where pid = :pid ");

    SqlParameterSource parameter = new MapSqlParameterSource()
        .addValue("pname", product.getPname())
        .addValue("quantity", product.getQuantity())
        .addValue("price", product.getPrice())
        .addValue("pid", pid);

    return template.update(stringBuffer.toString(), parameter);
  }

  /** 삭제
   * @param pid
   * @return
   */
  @Override
  public int delete(Long pid) {
    String sql = " delete from product where pid = :pid ";

    return template.update(sql, Map.of("pid", pid));
  }

  /** 목록
   * @return
   */
  @Override
  public List<Product> findAll() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" select pid, pname, quantity, price ");
    stringBuffer.append(" from product ");

    List<Product> list = template.query(stringBuffer.toString(), productRowMapper());

    return list;
  }

  @Override
  public boolean isExist(Long pid) {
    boolean isExist = false;
    String sql = " select count(*) from product where product_id = :product_id ";

    Map<String,Long> param = Map.of("pid",pid);
    Integer integer = template.queryForObject(sql, param, Integer.class);
    isExist = (integer > 0) ? true : false;
    return isExist;
  }

  @Override
  public int countOfRecord() {
    String sql = "select count(*) from product ";
    Map<String,String> param = new LinkedHashMap<>();
    Integer rows = template.queryForObject(sql, param, Integer.class);
    return rows;
  }
}
