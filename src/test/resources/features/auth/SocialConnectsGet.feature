  # language: ru
  @all
  Функция: Возвращает список социальных сетей с помощью которых доступна аутентификация/регистрация

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
    Сценарий: Отправить запрос для получения списка социальных сетей

      Когда выполнен GET запрос на URL "/social-connects" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |

      И ответ содержит статус код 200

      Тогда проверяем, что схема "social-connectsSchema.json" соответствует схеме из ответа