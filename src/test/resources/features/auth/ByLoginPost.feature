  # language: ru
  @all
  Функция: Аутентификация с помощью пары email или телефон + пароль

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
   Сценарий: Отправить запрос для аутентификации с помощью пары email или телефон + пароль

    Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
     |login               |password           |
     |v.ivanov@mail.ru    |VerySecureP@ssw0rd |

    Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
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
   Сценарий: Отправить запрос для аутентификации с помощью пары email + не соответствующий пароль

    Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
     |login               |password     |
     |v.ivanov@mail.ru    |BadP@ssw0rd  |

    Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Данные введены неправильно или такого аккаунта не существует"

    @negative
    Сценарий: Отправить запрос для аутентификации с помощью пары не существующий E-mail + пароль

     Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
      |login               |password            |
      |doesnotexist@mail   |VerySecureP@ssw0rd  |

     Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

     И ответ содержит статус код 400

     Затем проверяем, что в теле ответа "response" есть сообщение "Данные введены неправильно или такого аккаунта не существует"

   @negative
   Сценарий: Отправить запрос для аутентификации с помощью пары не корректный E-mail + пароль

    Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
     |login            |password            |
     |not@mail         |VerySecureP@ssw0rd  |

    Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Пожалуйста, введите корректный E-mail"

     @negative
     Сценарий: Отправить запрос для аутентификации на email без пароля

      Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
       |login               |
       |v.ivanov@mail.ru    |

      Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
       | type   | name         | value            |
       | header | content-type | application/json |
       | body   | body         | {dataBody}       |

      И ответ содержит статус код 400

      Затем проверяем, что в теле ответа "response" есть сообщение "Пожалуйста, введите пароль"

      @negative
      Сценарий: Отправить запрос для аутентификации без email

       Дано создан объект таблицы для сохранения в переменную "dataBody" для проверки аутентификации
        |password            |
        |VerySecureP@ssw0rd  |

       Когда выполнен POST запрос на URL "/auth/by-login" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

       И ответ содержит статус код 400

       Затем проверяем, что в теле ответа "response" есть сообщение "Пожалуйста, введите логин"