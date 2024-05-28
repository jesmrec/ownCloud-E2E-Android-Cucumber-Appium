@shares
Feature: Private Share

  As a user
  I want to share my content with other users in the platform
  So that the content is accessible and others can contribute

  Background: User is logged in
    Given user Alice is logged

  @createshare
  Rule: Create a share

  @smoke
  Scenario Outline: Correct share with user
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item>
    And Alice selects user Bob as sharee
    Then user Bob should have access to <item>
    And share should be created on <item> with the following fields
      | sharee | Bob |

    Examples:
      | type   | item       |
      | file   | Share1.txt |
      | folder | Share2     |

  Scenario Outline: Correct share with group
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item>
    And Alice selects group test as sharee
    Then group test should have access to <item>
    And share should be created on <item> with the following fields
      | group | test |

    Examples:
      | type   | item       |
      | file   | Share3.txt |
      | folder | Share4     |

        #Permissions
        # READ -> 1
        # UPDATE -> 2
        # CREATE -> 4
        # DELETE -> 8
        # SHARE -> 16

  @resharing
  Rule: Resharing

  Scenario: Reshare allowed
    Given the following items have been created in Alice account
      | file | Share5.txt |
    When Alice selects to share the file Share5.txt
    And Alice selects user Bob as sharee
    And Bob has reshared file Share5.txt with Charles with permissions 31
    Then user Bob should have access to Share5.txt
    And user Charles should have access to Share5.txt
    And share should be created on Share5.txt with the following fields
      | sharee | Bob |

      #not an Android, keeping ftm...
  Scenario: Reshare not allowed
    Given the following items have been created in Alice account
      | file | Share6.txt |
    And Alice has shared file Share6.txt with Bob with permissions 3
    When Bob has reshared file Share6.txt with Charles with permissions 31
    Then user Bob should have access to Share6.txt
    But Charles should not have access to Share6.txt

  Scenario: Reshare reflected
    Given the following items have been created in Alice account
      | file | Share7.txt |
    And Alice has shared file Share7.txt with Bob with permissions 31
    And Bob has reshared file Share7.txt with Charles with permissions 31
    And Alice selects to share the file Share7.txt
    Then Alice should see Bob as recipient
    And Alice should see Charles as recipient

  @editshare
  Rule: Edit an existing share

  Scenario Outline: Edit existing share on a file, changing permissions
    Given the following items have been created in Alice account
      | file | <item> |
    And Alice has shared file <item> with <user> with permissions 31
    When Alice selects to share the file <item>
    And Alice edits the share on file <item> with permissions <permissions>
    Then user <user> should have access to <item>
    And share should be created on <item> with the following fields
      | sharee      | <user>        |
      | permissions | <permissions> |

    Examples:
      | item        | user | permissions | Description
      | Share8.txt  | Bob  | 3           | edit
      | Share9.txt  | Bob  | 17          | share
      | Share10.txt | Bob  | 19          | edit and share
      | Share11.txt | Bob  | 1           | only read

  Scenario Outline: Edit existing share on a folder, changing permissions
    Given the following items have been created in Alice account
      | folder | <item> |
    And Alice has shared folder <item> with <user> with permissions 31
    When Alice selects to share the folder <item>
    And Alice edits the share on folder <item> with permissions <permissions>
    Then user <user> should have access to <item>
    And share should be created on <item> with the following fields
      | sharee      | <user>        |
      | permissions | <permissions> |

    Examples:
      | item    | user | permissions | Description       |
      | Share12 | Bob  | 1           | only read         |
      | Share13 | Bob  | 3           | update            |
      | Share14 | Bob  | 9           | delete            |
      | Share15 | Bob  | 13          | delete and create |
      | Share16 | Bob  | 17          | share             |

  @deleteshare
  Rule: Delete a share

  Scenario Outline: Delete existing share
    Given the following items have been created in Alice account
      | <type> | <item> |
    And Alice has shared folder <item> with Bob with permissions 31
    When Alice selects to share the <type> <item>
    And Alice deletes the share
    Then <item> should not be shared anymore with Bob
    And Bob should not have access to <item>

    Examples:
      | type   | item        |
      | file   | Share17.txt |
      | folder | Share18     |

  @shareshortcut @nooc10
  Rule: Public link Shortcut

  Scenario Outline: Content in shares shortcut
    Given the following items have been created in Bob account
      | <type> | <item> |
    And Bob has shared <type> <item> with Alice with permissions 31
    When Alice opens the public link shortcut
    Then Alice should see <item> in the shares list

    Examples:
      | type   | item        |
      | file   | Share19.txt |
      | folder | Share20     |