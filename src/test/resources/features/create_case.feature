Feature: Create and process a Pega case

  Scenario: Create a new AssistanceRequest case
    Given I have a valid access token
    When I create a new case
    Then the case is created successfully


