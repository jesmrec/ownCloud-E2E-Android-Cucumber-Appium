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

  Scenario Outline: Download a video that is previewable
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Download the item <name>
    Then the <type> <name> should be opened and previewed

    Examples:
      | type  | name      |
      | video | video.mp4 |

  Scenario Outline: Download a file that is damaged and can not be previewed
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Download the item <name>
    Then Alice should see the error previewing <name>
    And file <name> should be stored in device

    Examples:
      | type    | name        |
      | damaged | damaged.png |

  Scenario Outline: Markdown file rendered
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Download the item <name>
    Then Alice should see the following items
      | Markdown |
      | Text     |
    And the <type> <name> should be opened and previewed
    And <type> <name> should be stored in device

    Examples:
      | type | name        |
      | file | rendered.md |

  Scenario Outline: Download a file from Details view
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Details the item <name>
    And Alice clicks on the thumbnail
    Then the <type> <name> should be opened and previewed
    And the <type> <name> should be marked as downloaded
    And <type> <name> should be stored in device

    Examples:
      | type | name      |
      | file | text2.txt |

  @smoke
  Scenario Outline: Download an updated file
    Given the following items have been created in Alice account
      | <type> | <name> |
    And Alice selects to Download the item <name>
    When Alice closes the preview
    And file <name> is modified remotely adding "updated"
    And Alice selects to Download the item <name>
    Then Alice should see the file <name> with "updated"

    Examples:
      | type | name      |
      | file | text3.txt |
