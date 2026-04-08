Feature: Simple tests
  Demonstrates Qase annotation tags on Cucumber scenarios.

  @QaseId=401
  Scenario: Test with QaseId
    Then the test passes

  @QaseId=402 @QaseId=403
  Scenario: Test with multiple QaseIds
    Then the test passes

  @QaseId=404 @QaseTitle=Custom_test_title
  Scenario: Test with QaseTitle
    Then the test passes

  @QaseId=405 @QaseFields={"description":"Verifies_login","severity":"critical","priority":"high","layer":"e2e"} @QaseTags=smoke
  Scenario: Test with QaseFields
    Then the test passes

  @QaseId=406 @QaseSuite=Suite01
  Scenario: Test with QaseSuite
    Then the test passes

  @QaseId=407 @QaseSuite=Parent\tChild\tGrandchild
  Scenario: Test with nested QaseSuite
    Then the test passes

  @QaseId=408 @QaseIgnore
  Scenario: Test excluded from reporting
    Then the test passes

  @QaseId=409 @QaseTitle=Combined_annotations @QaseTags=regression
  Scenario: Test with combined annotations
    Then the test passes
