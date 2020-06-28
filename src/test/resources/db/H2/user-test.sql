/*Password is 12345*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (12, 'vladsmirn289@gmail.com', 'ABC', 'DEF', 'simpleUser', '$2a$08$nrygwfZ9hXl/DNfRoQfJO.3PFN39VLWCbaXhSj0SjyZKa.EdX8ckq', NULL);
/*Password is 67891*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (13, 'xwitting@powlowski.com', 'GHI', 'GKL', 'manager', '$2a$08$RsfpoJz/bmpQ4EJZYx8zf./EPe52VhqeywtUZ134vxSxxeiklPB5O', NULL);
/*Password is 01112*/
INSERT INTO client (id, email, first_name, last_name, login, password, patronymic) VALUES (14, 'goconnell@bernhard.com', 'MNO', 'PQR', 'admin', '$2a$08$su.TatC77P4oiIY2o0BpxeBj4KvLd2BK66zx1AmkcSmqCxmiz1lkO', NULL);

INSERT INTO client_roles (client_id, roles) VALUES (12, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (13, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (13, 'MANAGER');
INSERT INTO client_roles (client_id, roles) VALUES (14, 'USER');
INSERT INTO client_roles (client_id, roles) VALUES (14, 'MANAGER');
INSERT INTO client_roles (client_id, roles) VALUES (14, 'ADMIN');
