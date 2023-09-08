  # language: ru

  Функция: Получение списка населенных пунктов

    Сценарий: Отправить запрос для отчистки содержимого корзины

      Дано выполнен GET запрос на URL "/locations" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | accept       | application/json |
        | header | content-type | application/json |

      И ответ содержит статус код 200

      Тогда проверяем, что схема "locationsSchema.json" соответствует схеме из ответа