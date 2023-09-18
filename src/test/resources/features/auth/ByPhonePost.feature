  # language: ru
  @all
  Функция: Аутентификация по номеру телефона

   Предыстория: Необходимо получить гостевой токен
    Дано создан объект и сохранен в переменную "dataBody"
     |platform    |version |build             |
     |Android 12  |2.1.0   |Build/SP1A.210812 |

    Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

   @positive
   Сценарий: Отправить запрос для аутентификации по номеру телефона

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
     |phone          |
     |79234567890    |

    Когда выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 204

    И проверяем, что в заголовке "X-Auth-Token" пришел новый токен

    Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

    И выполняем GET запрос на URL "/profile" для получения данных из профиля пользователя. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |

    Затем получаем из ответа "response" ID профиля и проверяем, маска ID правильная

    @negative
    Сценарий: Отправить не валидный номер телефона для аутентификации по номеру телефона

     Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
      |phone          |
      |1234           |

     Когда выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

     И ответ содержит статус код 400

     Затем проверяем, что в теле ответа "response" есть сообщение "Указан неверный номер телефона"

    @negative
    Сценарий: Отправить не подтвержденный номер телефона для аутентификации по номеру телефона

     Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
      |phone              |
      |79997778866        |

     Когда выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

     И ответ содержит статус код 400

     Затем проверяем, что в теле ответа "response" есть сообщение "Телефон не подтвержден"

   @negative
   Сценарий: Отправить запрос без номера телефона для аутентификации

    Когда выполнен POST запрос на URL "/auth/by-phone" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Номер телефона обязателен для заполнения"