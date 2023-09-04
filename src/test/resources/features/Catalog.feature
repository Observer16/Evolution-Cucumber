  # language: ru

    Функция: Получение каталога товаров, а точнее иерархии категорий
      Пользователь должен получить всю иерархию категорий товаров

      Сценарий: Отправить запрос для получения иерархии категорий

        Дано выполнен GET запрос на URL "/categories". Полученный ответ сохранен в переменную "response"
          | type   | name         | value            |
          | header | accept       | application/json |
          | header | content-type | application/json |
        И ответ содержит статус код 200
        #Тогда получаем каталог товаров первого уровня из ответа "response" и записываем "<ID>" в переменную