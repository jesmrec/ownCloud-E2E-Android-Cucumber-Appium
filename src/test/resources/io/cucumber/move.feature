@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged

  Rule: Move to regular location

    @smoke
    Scenario Outline: Move an existent folder to another location
      Given the following items have been created in Alice account
        | <type> | <name>   |
        | folder | <target> |
      When Alice selects to Move the <type> <name>
      And Alice selects <target> as target folder
      Then Alice should not see <name> in the filelist anymore
      But Alice should see <name> inside the folder <target>

      Examples:
        | type   | name      | target    |
        | folder | move1     | Documents |
        | file   | move2.txt | Documents |

    Scenario Outline: Move an existent item to a new created folder in the picker
      Given the following items have been created in Alice account
        | <type> | <name> |
      When Alice selects to Move the <type> <name>
      And Alice creates new folder <target> in the folder picker
      And Alice selects <target> as target folder
      Then Alice should not see <name> in the filelist anymore
      But Alice should see <name> inside the folder <target>

      Examples:
        | type | name      | target |
        | file | move3.txt | move4  |

    @moveconflicts
    Rule: Move with conflicts

    Scenario: Move a folder to another place with same item name
      Given the following items have been created in Alice account
        | folder | move5       |
        | folder | move6       |
        | folder | move6/move5 |
      When Alice selects to Move the folder move5
      And Alice selects move6 as target folder
      And Alice fixes the conflict with keep both
      Then Alice should see 'move5 (1)' inside the folder move6

    Scenario: Move a folder to another place with same item name
      Given the following items have been created in Alice account
        | folder | move7       |
        | folder | move8       |
        | folder | move8/move7 |
      When Alice selects to Move the folder move7
      And Alice selects move8 as target folder
      And Alice fixes the conflict with replace
      Then Alice should see move7 inside the folder move8

  Rule: Move negative cases

    Scenario: Move a folder to itself
      Given the following items have been created in Alice account
        | folder | move9 |
      When Alice selects to Move the folder move9
      And Alice selects move9 as target folder
      Then Alice should see the following error
        | It is not possible to move a folder into a descendant |

    Scenario: Move a folder to same location
      Given the following items have been created in Alice account
        | file | move10.txt |
      When Alice selects to Move the file move10.txt
      And Alice selects / as target folder
      Then Alice should see the following error
        | The file exists already in the destination folder |

    Scenario: Move a folder to descendant
      Given the following items have been created in Alice account
        | folder | move11        |
        | folder | move11/move12 |
      When Alice selects to Move the folder move11
      And Alice selects move11/move12 as target folder
      Then Alice should see the following error
        | It is not possible to move a folder into a descendant |