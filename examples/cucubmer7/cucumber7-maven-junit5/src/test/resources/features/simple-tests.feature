# Created by gda at 21.10.24.
Feature: Simple tests
  Here are some simple tests

  Scenario: Without annotations success
    Then return true

  Scenario: Without annotations failed
    Then return false

  @QaseId=1
  Scenario: With QaseID success
    Then return true

  @QaseId=2
  Scenario: With QaseID failed
    Then return false

  @QaseTitle=Title_success
  Scenario: With title success
    Then return true

  @QaseTitle=Title_failed
  Scenario: With title failed
    Then return false

  @QaseSuite=Suite01
  Scenario: With suite success
    Then return true

  @QaseSuite=Suite01
  Scenario: With suite failed
    Then return false

  @QaseFields={"description":"Some_description","severity":"major"}
  Scenario: With fields success
    Then return true

  @QaseFields={"description":"Some_description","severity":"major"}
  Scenario: With fields failed
    Then return false

  @QaseIgnore
  Scenario: With ignore success
    Then return true

  @QaseIgnore
  Scenario: With ignore failed
    Then return false
