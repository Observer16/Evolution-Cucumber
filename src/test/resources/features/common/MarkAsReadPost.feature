  #language: ru
  @all
  Функция: Помечает объявление прочитанным
    Помечает объявление прочитанным

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
    Сценарий: Отправить запрос для отметки, что объявление прочитано

      Дано идентификатор объявления
        |e1fc4583-f64e-49a1-99be-f243a21c0b0d |

      Когда выполнен POST запрос на URL "/announcement/{announcementId}/mark-as-read" с headers и ID из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |

      И ответ содержит статус код 204

    @negative
    Сценарий: Отправить запрос с не существующим идентификатором

      Дано идентификатор объявления
        |e1fc4583-0000-49a1-99be-f243a21c0b0d |

      Когда выполнен POST запрос на URL "/announcement/{announcementId}/mark-as-read" с headers и ID из таблицы. Полученный ответ сохранен в переменную "response"
        | type   | name         | value            |
        | header | content-type | application/json |

      И ответ содержит статус код 404

      Затем проверяем, что в теле ответа "response" есть сообщение "Объявление не найдено"