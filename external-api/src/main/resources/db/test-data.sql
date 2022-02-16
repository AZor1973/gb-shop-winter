insert into authority (permission)
values ('product.create'),
       ('product.update'),
       ('product.read'),
       ('product.delete');


insert into account_role (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');


insert into role_authority (AUTHORITY_ID, ROLE_ID)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (3, 2);

select * from account_user;