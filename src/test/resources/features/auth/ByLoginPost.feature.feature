  # language: ru

  Функция: Аутентификация с помощью пары email или телефон + пароль

   Сценарий: Отправить запрос для аутентификации с помощью пары email или телефон + пароль

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации с помощью пары email или телефон + пароль
     |login               |password           |
     |v.ivanov@mail.ru    |VerySecureP@ssw0rd |

    Дано выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 204

    Затем проверяем, что в заголовке "X-Auth-Token" токен пришел новый

    Тогда получаем гостевой токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"