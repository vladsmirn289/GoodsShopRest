DELETE FROM basket_items;
DELETE FROM ordered_item;
DELETE FROM item;
DELETE FROM category;
DELETE FROM client_roles;
DELETE FROM orders;
DELETE FROM client;
DELETE FROM item_additional_images;
DELETE FROM image;

ALTER SEQUENCE hibernate_sequence restart with 1;