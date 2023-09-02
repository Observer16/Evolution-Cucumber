  #language: ru

Функция: Управление токеном
  Пользователь должен получить гостевой токен

  Структура сценария: Отправить запрос на получение гостевого токена

    Дано отправлен запрос на "/auth/token" и ответ записан в переменную "response"
      |platform  |version  |build  |
      |<platform>|<version>|<build>|
    И Response status code is: "<status>"
    Тогда получаем гостевой токен из заголовка ответа "response" и записываем его в переменную "X-Auth-Token"
    Примеры:
      |platform  |version |build                |status|
      |Android 12|2.1.0   |Build/SP1A.210812.016|204   |