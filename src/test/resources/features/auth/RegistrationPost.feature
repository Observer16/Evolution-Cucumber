  # language: ru
  @all
  Функция: Регистрация пользователя

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
  Сценарий: Отправить запрос с корректными данными для регистрации пользователя

    Дано создан рандомный номер телефона для сохранения в переменную "dataBody"
      | phone          |
      | {randomNum}    |

    Когда выполнен POST запрос на URL "/profile/registration" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

    И ответ содержит статус код 201

    И проверяем, что в заголовке "X-Auth-Token" пришел новый токен

    И проверяем, что тело ответа соответствует данным из таблицы
      | type   | name             | value  |
      | body   | isPhoneVerified  | true   |
      | body   | isEmailVerified  | false  |

    Затем получаем из ответа "response" ID профиля и проверяем, что он сформирован в соответствии с маской ID

  @negative
  Сценарий: Отправить запрос с ранее зарегистрированным номером телефона

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
      | phone          |
      | 79234567890    |

    Когда выполнен POST запрос на URL "/profile/registration" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

    И ответ содержит статус код 200

    Затем проверяем, что в теле ответа "response" есть сообщение "Пользователь с таким номером телефона уже зарегистрирован. Воспользуйтесь авторизацией или восстановлением пароля."

  @negative
  Сценарий: Отправить запрос с без номера телефона

    Когда выполнен POST запрос на URL "/profile/registration" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         |                  |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Не указан номер телефона"

  @negative
  Сценарий: Отправить запрос с неверным номером телефона

    Дано создан объект таблицы для сохранения в переменную "dataBody" при аутентификации по номеру телефона
      | phone     |
      | 792340    |

    Когда выполнен POST запрос на URL "/profile/registration" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      | type   | name         | value            |
      | header | content-type | application/json |
      | body   | body         | {dataBody}       |

    И ответ содержит статус код 400

    Затем проверяем, что в теле ответа "response" есть сообщение "Номер телефона указан неверно"