# API

### Введение

Для всех запросов необходимо передавать header:
```
Content-Type: application/json
```

Для большиства запросов требуется использовать базовую авторизацию (basic auth).

**Администратор**:
```
Login: admin@gmail.com
Password: admin
```

**Пользователь**:
```
Login: user@yandex.ru
Password: password
```

Данные для запросов передаются в формате **json**:
```
{
    "menuId": 100004,
    "name": "Item 123",
    "price": 100
}
```

### Пользователи

**Регистрация**

```
POST /rest/profile/register

{
    "name": "",
    "email": "",
    "password": ""
}
```

**Данные профиля**

```
GET /rest/profile
```

**Редактирование профиля**

```
PUT /rest/profile

{
    "name": "",
    "email": "",
    "password": ""
}
```

**Удаление аккаунта**

```
DELETE /rest/profile
```

### Администраторы

**Список всех пользователей**

```
GET /rest/admin/users
```

**Профиль пользователя**

```
GET /rest/admin/users/{id}
```

**Создание пользователя**

```
POST /rest/admin/users

{
    "name": "",
    "email": "",
    "password": ""
}
```

**Удаление пользователя**

```
DELETE /rest/admin/users/{id}
```

**Редактирование пользователя**

```
PUT /rest/admin/users/{id}

{
    "name": "",
    "email": "",
    "password": ""
}
```

**Профиль пользователя по email**

```
GET /rest/admin/users/by

{
    "email": "",
}
```

### Рестораны

**Список ресторанов**

```
GET /rest/restaurants
```

**Профиль ресторана**

```
GET /rest/restaurants/{id}
```

**Создать ресторан**

```
POST /rest/restaurants

{
    "name": "",
}
```

**Редактировать ресторан**

```
PUT /rest/restaurants/{id}

{
    "name": "",
}
```

**Удалить ресторан**

```
DELETE /rest/restaurants/{id}
```

### Меню

**Список меню**

```
GET /rest/restaurants/{id}
```

**Профиль меню**

```
GET /rest/restaurants/{id}/menu/{menu_id}
```

**Создать меню**

```
POST /rest/restaurants/{id}/menu/

{
    "restaurantId": "",
}
```

**Редактировать меню**

```
PUT /rest/restaurants/{id}/menu/{menu_id}

{
    "restaurantId": "",
}
```

**Удалить меню**

```
DELETE /rest/restaurants/{id}/menu/{menu_id}
```

### Блюдо

**Список меню**

```
GET /rest/restaurants/{id}/menu/{menu_id}/items
```

**Профиль блюда**

```
GET /rest/restaurants/{id}/menu/{menu_id}/items/{id}
```

**Создать блюдо**

```
POST /rest/restaurants/{id}/menu/{menu_id}/items

{
    "menuId": "",
    "name": "",
    "price": ""
}
```

**Редактировать блюдо**

```
PUT /rest/restaurants/{id}/menu/{menu_id}/items/{id}

{
    "menuId": "",
    "name": "",
    "price": ""
}
```

**Удалить блюдо**

```
DELETE /rest/restaurants/{id}/menu/{menu_id}/items/{id}
```

### Заказ меню

**Меню на текущий день**

```
GET /rest/menu
```

**Заказать меню**

```
POST /rest/menu/{menu_id}
```