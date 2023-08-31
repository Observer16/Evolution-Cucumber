  # language: ru
    # encoding: utf-8

    @PostToken
    Функционал: Отправить запрос на получение гостевого токена

      @success
      Сценарий: Отправить запрос на получение гостевого токена
        Когда пользователь отправляет запрос "<BASE_URL>/auth/token" для получение гостевого токена
        И получить ответ с кодом 204
        И получить гостевой токен
        И проверить, что гостевой токен не пустой
        Тогда сохранить гостевой токен в переменную