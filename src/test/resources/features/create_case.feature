Feature: Create and process a Pega case

  Scenario: Create a new TellUsMore-Work-Incident case
    Given I have a valid access token
    When I create a new case
    Then the case is created successfully
    When I request the assignment details
    Then the assignment details are returned
    When I perform update on the assignment category
    Then the assignment details are updated
    When I perform update on the assignment service details
    Then the assignment details are updated
    When I perform update on the assignment customer details
    Then the assignment details are updated

