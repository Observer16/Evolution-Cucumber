  # language: ru

  Функция: Отчистка корзины

    Сценарий: Отправить запрос для отчистки содержимого корзины

      Дано создан объект таблицы для сохранения в переменную "dataBody" при отчистки корзины
        |operation   |
        |erase       |

      Дано выполнен PUT запрос на URL "/basket" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 204

      #Тогда проверяем, что схема "basketSchema.json" соответствует схеме из ответа