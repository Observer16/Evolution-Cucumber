  # language: ru
  @all
  Функция: Аутентификация по email

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

    И выполняем GET запрос на URL "/profile" для получения данных из профиля пользователя. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |

    Затем получаем из ответа "response" ID профиля и проверяем, что маска ID правильная

    @negative
    Сценарий: Отправить запрос для аутентификации когда Email указан неверно

     Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации с помощью email
      |email            |
      |pease@mail       |

     Когда выполнен POST запрос на URL "/auth/by-email" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

     И ответ содержит статус код 400

     Затем проверяем, что в теле ответа "response" есть сообщение "Адрес электронной почты указан неверно"

   @negative
   Сценарий: Отправить запрос для аутентификации когда Email не подтвержден

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации с помощью email
     |email                  |
     |doesnotexist@mail.ru   |

    Когда выполнен POST запрос на URL "/auth/by-email" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Email не подтвержден"

   @negative
   Сценарий: Отправить запрос для аутентификации когда Email не передается

    Когда выполнен POST запрос на URL "/auth/by-email" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
     | type   | name         | value            |
     | header | content-type | application/json |
     | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Пожалуйста, введите email"