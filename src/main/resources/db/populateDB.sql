DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, creation_date_time, description, calories)
VALUES (100000, '2020-01-30 10:00:00', 'Еда 1 USER', 1000),
       (100000, '2020-01-30 13:00:00', 'Еда 2 USER', 700),
       (100000, '2020-01-30 18:00:00', 'Еда 3 USER', 299),

       (100000, '2020-01-31 10:00:00', 'Еда 4 USER', 700),
       (100000, '2020-01-31 13:00:00', 'Еда 5 USER', 700),
       (100000, '2020-01-31 18:00:00', 'Еда 6 USER', 400),
       (100000, '2020-01-31 23:59:59', 'Еда 7 USER', 400),

       (100001, '2020-01-29 10:00:00', 'Еда 1 ADMIN', 1000),
       (100001, '2020-01-29 13:00:00', 'Еда 2 ADMIN', 899),
       (100001, '2020-01-29 13:00:01', 'Еда 3 ADMIN', 100),
       (100001, '2020-01-29 18:00:00', 'Еда 4 ADMIN', 1),

       (100001, '2020-01-28 10:00:00', 'Еда 5 ADMIN', 1000),
       (100001, '2020-01-28 13:00:00', 'Еда 6 ADMIN', 1000),
       (100001, '2020-01-28 18:00:00', 'Еда 7 ADMIN', 1);
