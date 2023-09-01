Feature: Get Articles
  User needed get the list of Articles

  Scenario Template: Send request for getting list of articles

    Given Get Articles "<URL>" Request and get response with "<Status>" status
    Examples:
      | URL           | Status |
      | articles.json | 200    |