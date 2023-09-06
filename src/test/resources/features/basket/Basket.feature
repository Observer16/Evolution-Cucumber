  # language: ru

  Функция: Получение списка товаров в корзине
    Список товаров в корзине

    Сценарий: Отправить запрос для получения иерархии категорий

      Дано выполнен GET запрос на URL "/basket" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | accept       | application/json |
        | header | content-type | application/json |

      И ответ содержит статус код 200

      Тогда проверяем, что схема "basketSchema.json" соответствует схеме из ответа