 # language: ru

 Функция: Получение списка ранее просмотренных товаров

   Сценарий: Отправить запрос для получения иерархии категорий

     Дано выполнен GET запрос на URL "/viewed-products" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
       | type   | name         | value            |
       | header | accept       | application/json |
       | header | content-type | application/json |

     И ответ содержит статус код 200

     Тогда проверяем, что ответ "response" соответствует ожидаемой схеме