  # language: ru

    Функция: Получение каталога товаров, а точнее иерархии категорий
      Пользователь должен получить всю иерархию категорий товаров

      Сценарий: Отправить запрос для получения иерархии категорий

        Дано выполнен GET запрос на URL "/categories" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
          | type   | name         | value            |
          | header | accept       | application/json |
          | header | content-type | application/json |

        И ответ содержит статус код 200

        Тогда проверяем, что схема "categoriesSchema.json" соответствует схеме из ответа