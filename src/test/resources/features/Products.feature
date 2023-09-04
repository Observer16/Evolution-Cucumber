  # language: ru

  Функция: Получение списка товаров, с учетом условий фильтрации

    Сценарий: Отправить запрос на получение списка товаров

      Дано выполнен GET запрос на URL "/products?categoryId=1003001&filter=filter%5Bbrand%5D%3Dnike&sortBy=rating-desc&page=1&limit=12" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      |type    |name          |value             |
      |header  |content-type  |application/json  |
      И ответ содержит статус код 200