Feature: Simple tests
  Demonstrates Qase annotation tags on Cucumber scenarios.

  @QaseId=501
  Scenario: Test with QaseId
    Then the test passes

  @QaseId=502 @QaseId=503
  Scenario: Test with multiple QaseIds
    Then the test passes

  @QaseId=504 @QaseTitle=Custom_test_title
  Scenario: Test with QaseTitle
    Then the test passes

  @QaseId=505 @QaseFields={"description":"Verifies_login","severity":"critical","priority":"high","layer":"e2e"}
  Scenario: Test with QaseFields
    Then the test passes

  @QaseId=506 @QaseSuite=Suite01
  Scenario: Test with QaseSuite
    Then the test passes

  @QaseId=507 @QaseSuite=Parent\tChild\tGrandchild
  Scenario: Test with nested QaseSuite
    Then the test passes

  @QaseId=508 @QaseIgnore
  Scenario: Test excluded from reporting
    Then the test passes

  @QaseId=509 @QaseTitle=Combined_annotations
  Scenario: Test with combined annotations
    Then the test passes
