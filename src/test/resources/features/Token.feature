Feature: Управление токеном
  Пользователь должен получить гостевой токен

  Scenario Outline: : Отправить запрос на получение гостевого токена

    Given Отпрвить запрос на получение гостевого токена "<URL>" Request
    And Request body передает "<body>"
    And Response code is: "<status>"
    And Извлекаем токен из ответа
    Then Сохраняем токен в переменную "<x-auth-token>"
    Examples:
      | URL         | body    | status |
      | /auth/token | {"platform": "Android 12","version": "2.1.0","build": "Build/SP1A.210812.016"}| 200    |