

## # Review of OOP and SQL

This project (Nélio Alves  https://www.youtube.com/watch?v=xC_yKw3MYX4&t=413s)  is trying to simulate the back end part of an delivery app, 
that accept request, and also regiter this requests and display important data about the order such as: Date, status, price, itens, localization.

In this project it was used :
- Primary key, Foreingner key 
- DDL (create table, alter table)
- SQL
  - INSERT
  - SELECT
  - INNER JOIN
- Classes e objetos
- Encapsulation, get/set
- Enum
- Object compoition
- Collections (list, map)
- Access data in relational DB and instantiate corresponding objects


## Create and instantiate of the database
```sql
create table tb_order (
    id int8 generated by default as identity, 
    latitude float8, 
    longitude float8, 
    moment TIMESTAMP WITHOUT TIME ZONE, 
    status int4, 
    primary key (id)
);

create table tb_order_product (
    order_id int8 not null, 
    product_id int8 not null, 
    primary key (order_id, product_id)
);

create table tb_product (
    id int8 generated by default as identity, 
    description TEXT, 
    image_uri varchar(255), 
    name varchar(255), 
    price float8, 
    primary key (id)
);

alter table if exists tb_order_product add constraint fk_tb_order_product_tb_product 
foreign key (product_id) references tb_product;

alter table if exists tb_order_product add constraint fk_tb_order_product_tb_order 
foreign key (order_id) references tb_order;

INSERT INTO tb_product (name, price, image_Uri, description) VALUES 
('Pizza de Calabresa', 50.0, 'https://github.com/VitorInc/1.png', 'Pizza calabresa com queijo, molho e massa especial'),
('Pizza Quatro Queijos', 40.0, 'https://github.com/VitorInc/2.png', 'Pizza quatro queijos muito boa'),
('Pizza de Escarola', 60.0, 'https://github.com/VitorInc/3.png', 'Pizza escarola muito boa');

INSERT INTO tb_order (status, latitude, longitude, moment) VALUES 
(0, 213123, 12323, TIMESTAMP WITH TIME ZONE '2021-01-04T11:00:00Z'),
(1, 3453453, 3534534, TIMESTAMP WITH TIME ZONE '2021-01-05T11:00:00Z');

INSERT INTO tb_order_product (order_id, product_id) VALUES 
(1 , 1),
(1 , 2),
(2 , 2),
(2 , 3);
```

## Query to retrieve orders with your products
```sql
SELECT * FROM tb_order
INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id
INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id
```
