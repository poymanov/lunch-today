DROP TABLE restaurant_menu_items IF EXISTS;
DROP TABLE restaurant_menu IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ
  AS INTEGER
  START WITH 100000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    created_at       TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT restaurants_idx UNIQUE (name)
);

CREATE TABLE restaurant_menu
(
    id INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    date TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT restaurant_menu_idx UNIQUE (restaurant_id, date),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE restaurant_menu_items
(
    id INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_menu_id INTEGER NOT NULL,
    name VARCHAR(255),
    price INTEGER,
    CONSTRAINT restaurant_menu_items_idx UNIQUE (restaurant_menu_id, name),
    FOREIGN KEY (restaurant_menu_id) REFERENCES restaurant_menu (id) ON DELETE CASCADE
);

CREATE TABLE user_orders
(
    id INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    user_id INTEGER NOT NULL,
    restaurant_menu_id INTEGER NOT NULL,
    FOREIGN KEY (restaurant_menu_id) REFERENCES restaurant_menu (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT user_orders_idx UNIQUE (user_id, restaurant_menu_id)
);