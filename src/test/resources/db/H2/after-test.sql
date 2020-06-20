DELETE FROM item;
DELETE FROM category;
DELETE FROM client;
DELETE FROM orders;
DELETE FROM ordered_item;
DELETE FROM basket_items;

ALTER SEQUENCE hibernate_sequence restart with 1;