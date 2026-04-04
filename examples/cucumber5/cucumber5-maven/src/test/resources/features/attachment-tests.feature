Feature: Attachment tests
  Demonstrates Qase.comment() and Qase.attach() for all attachment types.

  @QaseId=512
  Scenario: Add a comment
    When a comment is added
    Then the test passes

  @QaseId=513
  Scenario: Attach a file
    When a file is attached
    Then the test passes

  @QaseId=514
  Scenario: Attach string content
    When string content is attached
    Then the test passes

  @QaseId=515
  Scenario: Attach byte array content
    When byte array content is attached
    Then the test passes

  @QaseId=516
  Scenario: Attachment inside a nested step
    When a nested step adds an attachment
    Then the test passes

  @QaseId=517
  Scenario: Multiple attachments in one scenario
    When a comment is added
    And string content is attached
    And byte array content is attached
    Then the test passes
