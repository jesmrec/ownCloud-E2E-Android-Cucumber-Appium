@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged

  Rule: Move to regular location

    @smoke
    Scenario Outline: Move an existent non-downloaded item to another location
      Given the following items have been created in Alice account
        | <type> | <name>   |
        | folder | <target> |
      When Alice selects to Move the <type> <name>
      And Alice selects <target> as target folder
      Then Alice should not see <name> in the filelist anymore
      But Alice should see '<name>' inside the folder <target>

      Examples:
        | type   | name      | target    |
        | folder | move1     | Documents |
        | file   | move2.txt | Documents |

    @smoke
    Scenario Outline: Move an existent downloaded file to another location
      Given the following items have been created in Alice account
        | <type> | <name>   |
        | folder | <target> |
      When Alice selects to Download the <type> <name>
      And Alice closes the preview
      And Alice selects to Move the <type> <name>
      And Alice selects <target> as target folder
      Then Alice should not see <name> in the filelist anymore
      But Alice should see '<name>' inside the folder <target>
      And file <target>/<name> should be stored in device
      But file <name> should not be stored in device

      Examples:
        | type | name      | target    |
        | file | move3.txt | Documents |

    Scenario Outline: Move an existent item to a new created folder in the picker
      Given the following items have been created in Alice account
        | <type> | <name> |
      When Alice selects to Move the <type> <name>
      And Alice creates new folder <target> in the folder picker
      And Alice selects <target> as target folder
      Then Alice should not see <name> in the filelist anymore
      But Alice should see '<name>' inside the folder <target>

      Examples:
        | type | name      | target |
        | file | move4.txt | move5  |

    @moveconflicts
  Rule: Move with conflicts

    Scenario: Move a folder to another place with same item name
      Given the following items have been created in Alice account
        | folder | move6       |
        | folder | move7       |
        | folder | move7/move6 |
      When Alice selects to Move the folder move6
      And Alice selects move7 as target folder
      And Alice fixes the conflict with keep both
      Then Alice should see 'move6 (1)' inside the folder move7

    Scenario: Move a folder to another place with same item name
      Given the following items have been created in Alice account
        | folder | move8       |
        | folder | move9       |
        | folder | move8/move8 |
      When Alice selects to Move the folder move8
      And Alice selects move9 as target folder
      And Alice fixes the conflict with replace
      Then Alice should see 'move8' inside the folder move9

  Rule: Move negative cases

    Scenario: Move a folder to itself
      Given the following items have been created in Alice account
        | folder | move10 |
      When Alice selects to Move the folder move10
      And Alice selects move10 as target folder
      Then Alice should see the following error
        | It is not possible to move a folder into a descendant |

    Scenario: Move a folder to same location
      Given the following items have been created in Alice account
        | file | move11.txt |
      When Alice selects to Move the file move11.txt
      And Alice selects / as target folder
      Then Alice should see the following error
        | The file exists already in the destination folder |

    Scenario: Move a folder to descendant
      Given the following items have been created in Alice account
        | folder | move12        |
        | folder | move12/move13 |
      When Alice selects to Move the folder move12
      And Alice selects move12/move13 as target folder
      Then Alice should see the following error
        | It is not possible to move a folder into a descendant |