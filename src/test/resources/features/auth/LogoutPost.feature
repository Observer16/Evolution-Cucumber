  # language: ru
  @all
  Функция: Выход из профиля

    Предыстория: Нужно войти в действующий профиль
      Дано создан объект и сохранен в переменную "dataBody"
        |platform    |version |build             |
        |Android 12  |2.1.0   |Build/SP1A.210812 |

      Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

      Затем создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
        |phone          |
        |79234567890    |

      И выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 204

      И проверяем, что в заголовке "X-Auth-Token" пришел новый токен

      И получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

      И выполняем GET запрос на URL "/profile" для получения данных из профиля пользователя. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |

      И получаем из ответа "response" ID профиля и проверяем, что маска ID правильная

    @positive
    Сценарий: Отправить запрос с корректными данными на получение гостевого токена

      Когда выполнен POST запрос на URL "/auth/logout" полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |

      И ответ содержит статус код 204