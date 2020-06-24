/*Password is 12345*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (1, 'vladsmirn289@gmail.com', 'A', 'B', 'simpleUser', '$2a$08$nrygwfZ9hXl/DNfRoQfJO.3PFN39VLWCbaXhSj0SjyZKa.EdX8ckq', NULL);
/*Password is 67891*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (2, 'xwitting@powlowski.com', 'C', 'D', 'manager', '$2a$08$RsfpoJz/bmpQ4EJZYx8zf./EPe52VhqeywtUZ134vxSxxeiklPB5O', NULL);
/*Password is 01112*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (3, 'goconnell@bernhard.com', 'E', 'F', 'admin', '$2a$08$su.TatC77P4oiIY2o0BpxeBj4KvLd2BK66zx1AmkcSmqCxmiz1lkO', NULL);

INSERT INTO client_roles (client_id, roles) VALUES (1, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (2, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (2, 'MANAGER');
INSERT INTO client_roles (client_id, roles) VALUES (3, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (3, 'MANAGER');
INSERT INTO client_roles (client_id, roles) VALUES (3, 'ADMIN');