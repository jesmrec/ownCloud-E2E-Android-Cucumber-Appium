@download
Feature: Download a file in the account

  As a user
  I want to download the items on my list to my device
  so that my content is also stored in the device

  Background: User is logged in
    Given user Alice is logged

  @smoke
  Scenario Outline: Download a file that is previewable
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Download the item <name>
    Then the <type> <name> should be opened and previewed
    And the <type> <name> should be marked as downloaded
    And file <name> should be stored in device

    Examples:
      | type  | name      |
      | file  | text1.txt |
      | image | blank.jpg |
      | audio | sound.mp3 |

  Scenario: Download a video that is previewable
    Given the following items have been created in Alice account
      | video | video.mp4 |
    When Alice selects to Download the item video.mp4
    Then the video video.mp4 should be opened and previewed

  Scenario: Download a file that is damaged and can not be previewed
    Given the following items have been created in Alice account
      | damaged | damaged.png |
    When Alice selects to Download the item damaged.png
    Then Alice should see the error previewing damaged.png
    And file damaged.png should be stored in device

  Scenario: Markdown file rendered
    Given the following items have been created in Alice account
      | file | rendered.md |
    When Alice selects to Download the item rendered.md
    Then Alice should see the following items
      | Markdown |
      | Text     |
    And the file rendered.md should be opened and previewed
    And file rendered.md should be stored in device

  Scenario: Download a file from Details view
    Given the following items have been created in Alice account
      | file | text2.txt |
    When Alice selects to Details the item text2.txt
    And Alice clicks on the thumbnail
    Then the file text2.txt should be opened and previewed
    And the file text2.txt should be marked as downloaded
    And file text2.txt should be stored in device

  @smoke
  Scenario: Download an updated file
    Given the following items have been created in Alice account
      | file | text3.txt |
    And Alice selects to Download the item text3.txt
    When Alice closes the preview
    And file text3.txt is modified externally adding "updated"
    And Alice selects to Download the item text3.txt
    Then Alice should see the file text3.txt with "updated"
