  # language: ru

  Функция: Отчистка корзины

    Сценарий: Отправить запрос для отчистки содержимого корзины

      Дано создан объект таблицы для сохранения в переменную "dataBody"
        |operation   |
        |erase       |

      Дано выполнен PUT запрос на URL "/basket" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | accept       | application/json |
        | header | content-type | application/json |

      И ответ содержит статус код 200

      #Тогда проверяем, что схема "basketSchema.json" соответствует схеме из ответа