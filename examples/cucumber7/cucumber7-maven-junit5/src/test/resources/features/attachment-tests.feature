Feature: Attachment tests
  Demonstrates Qase.comment() and Qase.attach() for all attachment types.

  @QaseId=412
  Scenario: Add a comment
    When a comment is added
    Then the test passes

  @QaseId=413
  Scenario: Attach a file
    When a file is attached
    Then the test passes

  @QaseId=414
  Scenario: Attach string content
    When string content is attached
    Then the test passes

  @QaseId=415
  Scenario: Attach byte array content
    When byte array content is attached
    Then the test passes

  @QaseId=416
  Scenario: Attachment inside a nested step
    When a nested step adds an attachment
    Then the test passes

  @QaseId=417
  Scenario: Multiple attachments in one scenario
    When a comment is added
    And string content is attached
    And byte array content is attached
    Then the test passes
