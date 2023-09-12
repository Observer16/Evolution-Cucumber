  #language: ru

    Функция: Обновление токена (пролонгация)
      Пользователь должен получить новый токен

      Сценарий: Отправить запрос на обновление токена

        Дано создан объект и сохранен в переменную "dataBody"
          |platform    |version |build             |
          |Android 12  |2.1.0   |Build/SP1A.210812 |
          |Android 12  |        |Build/SP1A.210812 |
          |Android 12  |2.1.0   |                  |

        Затем выполнен PATCH запрос на URL "/auth/token" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
          | type   | name         | value            |
          | header | content-type | application/json |
          | body   | body         | {dataBody}       |

        И ответ содержит статус код 204

        Затем проверяем, что в заголовке "X-Auth-Token" пришел новый токен

        Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"