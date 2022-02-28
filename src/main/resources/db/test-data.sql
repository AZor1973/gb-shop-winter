insert into account_user (username, password, firstname, lastname, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED,
                          CREDENTIALS_NON_EXPIRED, enabled, email, status, phone)
values ('user', '$2a$10$rSL9AI8zo3jevAAjOJtwLOCknuqPWMQsw5dMFoCSi0IdTwyz7kuMi', 'Иван', 'Петров', true, true, true, true, 'test@mail.ru', 'ACTIVE', '+7999999999'),
       ('admin', '$2a$10$MTOD2wE6bZqbgm0DfG41be44FCTnNyeVkkksycfWCJaFPmgHazOUG', 'Владимир', 'Николаев', true, true, true, true, 'test@mail.ru', 'ACTIVE', '+7999999999');

insert into authority (permission)
values ('product.create'),
       ('product.update'),
       ('product.read'),
       ('product.delete'),
       ('manufacturer.create'),
       ('manufacturer.update'),
       ('manufacturer.read'),
       ('manufacturer.delete'),
       ('category.create'),
       ('category.update'),
       ('category.read'),
       ('category.delete'),
       ('order.create'),
       ('order.update'),
       ('order.read'),
       ('order.delete');



insert into account_role (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

drop table account_role;

insert into role_authority (AUTHORITY_ID, ROLE_ID)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (3, 2),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1),
       (13, 1),
       (14, 1),
       (15, 1),
       (16, 1);

drop table role_authority;

insert into user_role(USER_ID, ROLE_ID)
values (1, 2),
       (2, 1);

drop table user_role;