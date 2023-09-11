  # language: ru

  Функция: Аутентификация по email

   Сценарий: Отправить запрос для аутентификации с помощью email

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации с помощью email
     |email            |
     |pease@mail.ru    |

    Когда выполнен POST запрос на URL "/auth/by-email" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 204

    Затем проверяем, что в заголовке "X-Auth-Token" пришел новый токен

    Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"