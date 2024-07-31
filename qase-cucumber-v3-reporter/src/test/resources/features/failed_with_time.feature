Feature: Failed feature

  @caseId=123
  Scenario: Failed scenario
    When timeout 3 seconds
    Given failed step
