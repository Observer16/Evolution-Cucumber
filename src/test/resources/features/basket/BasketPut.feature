  # language: ru
  @all
  Функция: Отчистка корзины

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