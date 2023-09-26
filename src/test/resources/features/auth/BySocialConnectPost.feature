  # language: ru
  @all
  Функция: Аутентификация в системе с помощью OAuth2 токена / Привязка соц сети

    Предыстория: Нужно войти в действующий профиль
      Дано создан объект и сохранен в переменную "dataBody"
        |platform    |version |build             |
        |Android 12  |2.1.0   |Build/SP1A.210812 |

      Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

    @positive
    Сценарий: Отправить запрос с именем провайдера и кодом доступа соц сети

      Дано создан объект BySocialConnect и сохранен в переменную "dataBody"
        |provider |code |
        |google   |1234 |

      Затем выполнен POST запрос на URL "/auth/by-social-connect" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 204

      И проверяем, что в заголовке "X-Auth-Token" пришел новый токен

      Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

    @negative
    Сценарий: Отправить запрос с именем провайдера и кодом доступа соц сети, но неверным кодом доступа

      Дано создан объект BySocialConnect и сохранен в переменную "dataBody"
        |provider |code |
        |google   |0000 |

      Затем выполнен POST запрос на URL "/auth/by-social-connect" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 400

      Затем проверяем, что в теле ответа "response" есть сообщение "Неверный код авторизации"

    @negative
    Сценарий: Отправить запрос с именем провайдера и кодом доступа соц сети, но неверным именем провайдера

      Дано создан объект BySocialConnect и сохранен в переменную "dataBody"
        |provider      |code |
        |YandexOAuth   |1234 |

      Затем выполнен POST запрос на URL "/auth/by-social-connect" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 400

      Затем проверяем, что в теле ответа "response" есть сообщение "Указанный провайдер аутентификации не поддерживается"

    @negative
    Сценарий: Отправить запрос без именем провайдера но с кодом доступа соц сети

      Дано создан объект BySocialConnect и сохранен в переменную "dataBody"
        |provider |code |
        |         |1234 |

      Затем выполнен POST запрос на URL "/auth/by-social-connect" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 400

      Затем проверяем, что в теле ответа "response" есть сообщение "Указанный провайдер аутентификации не поддерживается"

    @negative
    Сценарий: Отправить запрос с именем провайдера но без кода доступа

      Дано создан объект BySocialConnect и сохранен в переменную "dataBody"
        |provider  |code |
        |google    |     |

      Затем выполнен POST запрос на URL "/auth/by-social-connect" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 400

      Затем проверяем, что в теле ответа "response" есть сообщение "Не указан код или токен доступа"