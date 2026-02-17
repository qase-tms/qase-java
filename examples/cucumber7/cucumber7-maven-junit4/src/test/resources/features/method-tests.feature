# Created by gda at 21.10.24.
Feature: Method tests
  Here are some method tests

  Scenario: with comment success
    When add comment
    Then return true

  Scenario: with comment failed
    When add comment
    Then return false

  Scenario: with file attachment success
    When add attachments from file
    Then return true

  Scenario: with file attachment failed
    When add attachments from file
    Then return false

  Scenario: with content attachment success
    When add attachments from content
    Then return true

  Scenario: with content attachment failed
    When add attachments from content
    Then return false
