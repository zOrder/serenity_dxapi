Feature: Employee Details

  @GetEmployee
  Scenario: Get the details of employee
    Given I send a request to endpoint
    Then the API should return status 200
    And Response should contains employee name "Tiger Nixon"