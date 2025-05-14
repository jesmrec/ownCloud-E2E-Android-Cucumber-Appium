@delete
Feature: Delete item

  As a user
  I want to be able to delete content from my account
  so that i can get rid of the files and folders i do not need anymore

  Background: User is logged in
    Given user Alice is logged

  @smoke
  Scenario Outline: Delete an existent item remote
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Remove the <type> <name>
    And Alice accepts the remote deletion of <type>
    Then Alice should not see "<name>" in the filelist anymore

    Examples:
      | type   | name           |
      | folder | Deletefolder   |
      | file   | Deletefile.txt |

  Scenario Outline: Delete an downloaded item locally
    Given the following items have been created in Alice account
      | <type> | <name> |
    And Alice selects to Download the <type> <name>
    And Alice closes the preview
    When Alice selects to Remove the <type> <name>
    And Alice accepts the local deletion of <type>
    Then Alice should see "<name>" in the filelist
    And file <name> should not be stored in device

    Examples:
      | type | name          |
      | file | filelocal.txt |

  Scenario Outline: Delete an downloaded item remotely
    Given the following items have been created in Alice account
      | <type> | <name> |
    And Alice selects to Download the <type> <name>
    And Alice closes the preview
    When Alice selects to Remove the <type> <name>
    And Alice accepts the remote deletion of <type>
    Then Alice should not see "<name>" in the filelist anymore
    And file <name> should not be stored in device

    Examples:
      | type | name           |
      | file | filetemote.txt |

  Scenario: Delete several shows correct message
    Given the following items have been created in Alice account
      | file | delete1.txt |
      | file | delete2.txt |
      | file | delete3.txt |
      | file | delete4.txt |
      | file | delete5.txt |
    When Alice long presses over delete1.txt
    When Alice multiselects the following items
      | delete2.txt |
      | delete3.txt |
    And Alice selects to Delete
    Then Alice should see the following message
      | Do you really want to remove these 3 items? |
