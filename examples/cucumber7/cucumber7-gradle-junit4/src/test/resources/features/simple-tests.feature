Feature: Simple tests
  Demonstrates Qase annotation tags on Cucumber scenarios.

  Scenario: Test without annotations
    Then the test passes

  @QaseId=1
  Scenario: Test with QaseId
    Then the test passes

  @QaseId=2 @QaseId=3
  Scenario: Test with multiple QaseIds
    Then the test passes

  @QaseTitle=Custom_test_title
  Scenario: Test with QaseTitle
    Then the test passes

  @QaseFields={"description":"Verifies_login","severity":"critical","priority":"high","layer":"e2e"}
  Scenario: Test with QaseFields
    Then the test passes

  @QaseSuite=Suite01
  Scenario: Test with QaseSuite
    Then the test passes

  @QaseSuite=Parent\tChild\tGrandchild
  Scenario: Test with nested QaseSuite
    Then the test passes

  @QaseIgnore
  Scenario: Test excluded from reporting
    Then the test passes

  @QaseId=100 @QaseTitle=Combined_annotations
  Scenario: Test with combined annotations
    Then the test passes
