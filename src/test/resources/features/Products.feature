  # language: ru

  Функция: Получение списка товаров, с учетом условий фильтрации

    Сценарий: Отправить запрос на получение списка товаров

      Дано выполнен GET запрос на URL "/products" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      |type      |name          |value             |
      |header    |content-type  |application/json  |
      |parameter |categoryId    |1003001           |
      |parameter |page          |1                 |
      |parameter |limit         |2                 |

      И ответ содержит статус код 200

      Тогда проверяем, что ответ "response" соответствует ожидаемой схеме