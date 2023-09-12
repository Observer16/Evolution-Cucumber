  # language: ru
  @all
  Функция: Изменить населенный пункт
    Изменяет текущий населенный пункт пользователя

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
    Сценарий: Отправить запрос на изменение текущего населенного пункта пользователя и обновление токена

      Дано создан объект таблицы для сохранения в переменную "dataBody"
        |locationId  |
        |30640299    |
        |1547800299  |

      Затем выполнен POST запрос на URL "/state/location" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |
        | body   | body         | {dataBody}       |

      И ответ содержит статус код 204

      И проверяем, что в заголовке "X-Auth-Token" пришел новый токен

      Тогда получаем токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"