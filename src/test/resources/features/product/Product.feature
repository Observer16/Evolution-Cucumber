  # language: ru

  Функция: Получение расширенной информации товара по идентификатору

    Сценарий: Отправить запрос для получения иерархии категорий

      Дано продукт с идентификатором
        |27963050299 |

      Дано выполнен GET запрос на URL "/product/{productId}" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | accept       | application/json |
        | header | content-type | application/json |

      И ответ содержит статус код 200

      Тогда проверяем, что схема "productProductIdSchema.json" соответствует схеме из ответа