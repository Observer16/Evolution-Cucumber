  # language: ru
  @all
  Функция: Получение списка товаров, с учетом условий фильтрации

    Предыстория: Необходимо получить гостевой токен
      Дано создан объект и сохранен в переменную "dataBody"
        |platform    |version |build             |
        |Android 12  |2.1.0   |Build/SP1A.210812 |

      Когда выполнен POST запрос на URL "/auth/token" и ответ записан в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"

    @positive
    Сценарий: Отправить запрос на получение списка товаров из заданной категории

      Когда выполнен GET запрос на URL "/products" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
      |type      |name          |value             |
      |header    |content-type  |application/json  |
      |parameter |categoryId    |1003001           |
      |parameter |page          |1                 |
      |parameter |limit         |1                 |

      И ответ содержит статус код 200

      Тогда проверяем, что схема "productsSchema.json" соответствует схеме из ответа