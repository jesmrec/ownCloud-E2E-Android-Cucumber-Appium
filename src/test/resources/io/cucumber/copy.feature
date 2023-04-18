@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user Alice is logged

  @smoke
  Scenario Outline: Copy an existent item to another location
    Given the following items have been created in the account
      | <type>   | <name>    |
      | folder   | Documents |
    When Alice selects to Copy the <type> <name>
    And Alice selects <space> as space
    And Alice selects <target> as target folder
    Then Alice should see <name> in the filelist as original
    And Alice should see <name> inside the folder <target>

    Examples:
      | type     |  name          | target     | space     |
      | folder   |  Copyfolder    | Documents  | Personal  |
      | file     |  Copyfile.txt  | Documents  | Personal  |

  Scenario: Copy a folder to another place with same item name
    Given the following items have been created in the account
      | folder   | copy2        |
      | folder   | copy3        |
      | folder   | copy2/copy3  |
    When Alice selects to Copy the folder copy3
    And Alice selects Personal as space
    And Alice selects copy2 as target folder
    Then Alice should see 'copy3 (2)' inside the folder copy2

  Scenario: Copy an existent item to a new created folder in the picker
    Given the following items have been created in the account
      | file | copy4.txt |
    When Alice selects to Copy the file copy4.txt
    And Alice selects Personal as space
    And Alice creates new folder copy5 in the folder picker
    And Alice selects copy5 as target folder
    Then Alice should see copy4.txt inside the folder copy5

  @nooc10
  Scenario: Copy a file to same place (duplication)
    Given the following items have been created in the account
      | file | copy5.txt |
    When Alice selects to Copy the file copy5.txt
    And Alice selects Personal as space
    And Alice selects / as target folder
    Then Alice should see 'copy5 (2).txt' in the filelist as original

  Scenario: Copy a folder to itself
    Given the following items have been created in the account
      | folder | copy6 |
    When Alice selects to Copy the folder copy6
    And Alice selects Personal as space
    And Alice selects copy6 as target folder
    Then Alice should see the following error
      | It is not possible to copy a folder into a descendant |

  Scenario: Copy a folder to descendant
    Given the following items have been created in the account
      | folder | copy7       |
      | folder | copy7/copy8 |
    When Alice selects to Copy the folder copy7
    And Alice selects Personal as space
    And Alice selects copy7/copy8 as target folder
    Then Alice should see the following error
      | It is not possible to copy a folder into a descendant |