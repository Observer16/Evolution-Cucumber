# language: ru
@all
Функция: Управление токеном
  Пользователь должен получить гостевой токен

  @correct
  Сценарий: Отправить запрос с корректными данными на получение гостевого токена

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

  @fail
  Сценарий: Отправить запрос без передачи обязательных параметров на получение гостевого токена

    Дано создан объект и сохранен в переменную "dataBody"
      |platform    |version |build             |
      |            |2.1.0   |Build/SP1A.210812 |

    Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"