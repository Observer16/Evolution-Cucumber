  #language: ru

Функция: Управление токеном
  Пользователь должен получить гостевой токен

  Сценарий: Отправить запрос на получение гостевого токена

    Дано создан объект и сохранен в переменную "dataBody"
      |platform    |version |build             |
      |Android 12  |2.1.0   |Build/SP1A.210812 |
      |Android 12  |        |Build/SP1A.210812 |
      |Android 12  |2.1.0   |                  |

    Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

    И ответ содержит статус код 204

    Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"