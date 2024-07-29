Feature: New case with examples

  Scenario Outline: success with Positive Examples
    Given success step with parameter <a>
    Given success step with parameter <b>

    Examples:
      | a   | b   |
      | "1" | "2" |
      | "3" | "4" |
      | "5" | "6" |
      | "7" | "8" |

  Scenario: Success scenario
    Given success step