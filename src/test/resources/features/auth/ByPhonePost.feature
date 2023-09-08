  # language: ru

  Функция: Аутентификация по номеру телефона

   Сценарий: Отправить запрос для аутентификации по номеру телефона

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
     |phone          |
     |79234567890    |

    Дано выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 204

    Затем проверяем, что в заголовке "X-Auth-Token" токен пришел новый

    Тогда получаем гостевой токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"