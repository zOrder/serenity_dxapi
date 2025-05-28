Feature: Create and process a Pega case

  Scenario: Create a new TellUsMore-Work-Incident case
    Given I have a valid access token
    When I create a new case
    Then the case is created successfully
    When I request the assignment details
    Then the assignment details are returned

