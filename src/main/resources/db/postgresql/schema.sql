create sequence hibernate_sequence start 1 increment 1

create table basket_items (
   user_id int8 not null,
    item_id int8 not null,
    primary key (user_id, item_id)
)

create table category (
   id int8 not null,
    name varchar(255),
    parent_id int8,
    primary key (id)
)

create table client (
   id int8 not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    password varchar(255),
    patronymic varchar(255),
    primary key (id)
)

create table item (
   id int8 not null,
    characteristics varchar(255),
    code varchar(255),
    count int8,
    created_on date,
    description varchar(5000),
    image oid,
    name varchar(255),
    price float8,
    weight float8,
    category_id int8 not null,
    item_id int8,
    primary key (id)
)

create table orders (
   id int8 not null,
    city varchar(255),
    country varchar(255),
    phone_number varchar(255),
    street varchar(255),
    zip_code varchar(255),
    created_on timestamp,
    order_status varchar(255),
    ordered_count int8,
    payment_method varchar(255),
    track_number varchar(255),
    client_id int8,
    primary key (id)
)

alter table if exists basket_items
   add constraint FKpl11cw0c9nrsuet0xa7vcc7xr
   foreign key (item_id)
   references item

alter table if exists basket_items
   add constraint FKsek92n2afst4jvv8xihigqv5d
   foreign key (user_id)
   references client

alter table if exists category
   add constraint FK2y94svpmqttx80mshyny85wqr
   foreign key (parent_id)
   references category

alter table if exists item
   add constraint FK2n9w8d0dp4bsfra9dcg0046l4
   foreign key (category_id)
   references category

alter table if exists item
   add constraint FKrid3br8h0y448syw9ec5rjdy0
   foreign key (item_id)
   references orders

alter table if exists orders
   add constraint FK17yo6gry2nuwg2erwhbaxqbs9
   foreign key (client_id)
   references client