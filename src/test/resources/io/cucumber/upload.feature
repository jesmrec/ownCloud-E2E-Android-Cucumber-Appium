@upload
Feature: Upload new content

  As a user
  I want to upload files to my account
  so that those files will be synced from now on

  Background: User is logged in
    Given user Alice is logged

  Rule: Upload a single file

    @smoke
    Scenario Outline: Upload a single file to root folder
      Given a file <name> exists in the device
      When Alice selects the option Upload File
      And Alice selects <name> to upload
      Then Alice should see '<name>' in the filelist
      And Alice should see <name> as <status> in the uploads view
      And the file <name> should be stored in the account

      Examples:
        | name        | status   |
        | damaged.png | uploaded |

    Scenario Outline: Upload a single file to non-root folder
      Given the following items have been created in Alice account
        | type   | name     |
        | folder | <target> |
      And a file <name> exists in the device
      And Alice browses into <target>
      When Alice selects the option Upload File
      And Alice selects <name> to upload
      Then Alice should see '<path>' in the filelist
      And Alice should see <name> as <status> in the uploads view

      Examples:
        | name      | target  | path              | status   |
        | blank.jpg | upload1 | upload1/blank.jpg | uploaded |

    @camera @noci
    Scenario: Upload a picture from the camera
      When Alice selects the option Picture from Camera
      And Alice takes a picture
      Then Alice should see the following item
        | IMG_ |
