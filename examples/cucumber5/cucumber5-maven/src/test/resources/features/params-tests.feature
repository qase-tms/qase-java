Feature: Parameterized tests
  Demonstrates Scenario Outline with Examples table and Qase tags.

  @QaseId=50
  Scenario Outline: Login with different credentials
    Given a user with username "<username>"
    When the user attempts to login
    Then the login result is "<result>"

    Examples:
      | username | result  |
      | admin    | success |
      | guest    | limited |
      | invalid  | denied  |

  Scenario Outline: Process items with quantities
    Given an item "<item>" with quantity <quantity>
    When the item is processed
    Then the processing completes

    Examples:
      | item   | quantity |
      | Widget | 5        |
      | Gadget | 10       |
      | Gizmo  | 1        |
