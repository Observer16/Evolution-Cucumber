Feature: Get Articles
  User needed get the list of Articles

  Scenario Template: : Sent request for getting articles

    Given Get Articles "<URL>" Request
    Then Response code is: "<status>"
    Examples:
      | URL           | status |
      | articles.json | 200    |
      |               | 404    |
      | wrong.json    | 404    |