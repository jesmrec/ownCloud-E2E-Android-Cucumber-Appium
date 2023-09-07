@upload @ignore
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
        When Alice selects the option upload
        And Alice selects <name> to upload
        Then Alice should see <name> in the filelist
        And Alice should see <name> as <status> in the uploads view

        Examples:
          | name     | status   |
          | AAAA.txt | uploaded |

      Scenario Outline: Upload a single file to non-root folder
        Given the following items have been created in the account
          | folder | <target> |
        And a file <name> exists in the device
        And Alice browses into <target>
        When Alice selects the option upload
        And Alice selects <name> to upload
        Then Alice should see <path> in the filelist
        And Alice should see <name> as <status> in the uploads view

        Examples:
          | name      | target  | path              | status   |
          | land1.jpg | upload1 | upload1/land1.jpg | uploaded |