DELETE FROM restaurant_menu;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurants (alias, name) VALUES
('restaurant_1', 'Restaurant #1'),
('restaurant_2', 'Restaurant #2');

INSERT INTO restaurant_menu (restaurant_id) VALUES
(100002),
(100003);

INSERT INTO restaurant_menu_items (restaurant_menu_id, name, price) VALUES
(100004, 'Item 1', 100),
(100004, 'Item 2', 200),
(100005, 'Item 1', 100),
(100005, 'Item 2', 200);