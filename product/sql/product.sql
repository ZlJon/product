--테이블 생성
create table product(
    pid  number(10),
    pname       varchar(30),
    quantity    number(20),
    price       number(20)
);
--기본키
alter table product add constraint product_product_id_pk primary key(pid);

--시퀀스생성
create sequence product_pid_seq;

commit;



--생성 쿼리문--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '', 0, 0);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '', 0, 0);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '', 0, 0);

--조회 쿼리문--
select product_id, pname, quantity, price
  from product
 where product_id = 0;

--수정 쿼리문--
update product
   set pname = '컴퓨터',
       quantity = 0,
       price = 0;

--삭제 쿼리문--
delete from product where product_id = 0;

--전체조회-
select product_id,pname,quantity,price from product;

