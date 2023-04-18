@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged

  @smoke
  Scenario Outline: Move an existent folder to another location
    Given the following items have been created in the account
      | <type>   | <name>    |
      | folder   | Documents |
    When Alice selects to Move the <type> <name>
    And Alice selects <target> as target folder
    Then Alice should not see <name> in the filelist anymore
    But Alice should see <name> inside the folder <target>

    Examples:
      | type     |  name          | target     |
      | folder   |  Movefolder    | Documents  |
      | file     |  Movefile.txt  | Documents  |

  Scenario: Move a folder to another place with same item name
    Given the following items have been created in the account
      | folder | move1       |
      | folder | move2       |
      | folder | move2/move1 |
    When Alice selects to Move the folder move1
    And Alice selects move2 as target folder
    Then Alice should see 'move1 (2)' inside the folder move2

  Scenario: Move an existent item to a new created folder in the picker
    Given the following items have been created in the account
      | file | move2.txt |
    When Alice selects to Move the file move2.txt
    And Alice creates new folder move3 in the folder picker
    And Alice selects move3 as target folder
    Then Alice should not see move2.txt in the filelist anymore
    But Alice should see move2.txt inside the folder move3

  Scenario: Move a folder to itself
    Given the following items have been created in the account
      | folder | move4 |
    When Alice selects to Move the folder move4
    And Alice selects move4 as target folder
    Then Alice should see the following error
      | It is not possible to move a folder into a descendant |

  Scenario: Move a folder to same location
    Given the following items have been created in the account
      | file | move5.txt |
    When Alice selects to Move the file move5.txt
    And Alice selects / as target folder
    Then Alice should see the following error
      | The file exists already in the destination folder |

  Scenario: Move a folder to descendant
    Given the following items have been created in the account
      | folder | move6       |
      | folder | move6/move7 |
    When Alice selects to Move the folder move6
    And Alice selects move6/move7 as target folder
    Then Alice should see the following error
      | It is not possible to move a folder into a descendant |