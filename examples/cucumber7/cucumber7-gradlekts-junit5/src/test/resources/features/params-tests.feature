Feature: Parameterized tests

  Scenario Outline: Test with parameters
    Given I have a parameter <param>
    When I do something with the parameter
      | transaction | types          |
      | transfer    | manual, mobile |
      | inward      | manual, mobile |
    Then I should see the result

    Examples:
      | param  |
      | value1 |
      | value2 |
      | value3 |
