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

  @editshare
  Rule: Edit an existing share

  Scenario Outline: Edit existing share on a file, changing permissions
    Given the following items have been created in Alice account
      | file | <item> |
    And Alice has shared file <item> with <user> with permissions 31
    When Alice selects to share the file <item>
    And Alice edits the share on file <item> with permissions <permissions>
    Then user <user> should have access to <item>
    And share should be edited on <item> with the following fields
      | sharee      | <user>        |
      | permissions | <permissions> |

    Examples:
      | item       | user | permissions | Description |
      | Share5.txt | Bob  | 3           | edit        |
      | Share6.txt | Bob  | 1           | only read   |

  Scenario Outline: Edit existing share on a folder, changing permissions
    Given the following items have been created in Alice account
      | folder | <item> |
    And Alice has shared folder <item> with <user> with permissions 31
    When Alice selects to share the folder <item>
    And Alice edits the share on folder <item> with permissions <permissions>
    Then user <user> should have access to <item>
    And share should be edited on <item> with the following fields
      | sharee      | <user>        |
      | permissions | <permissions> |

    Examples:
      | item    | user | permissions | Description       |
      | Share7  | Bob  | 1           | only read         |
      | Share8  | Bob  | 3           | update            |
      | Share9  | Bob  | 9           | delete            |
      | Share10 | Bob  | 13          | delete and create |

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
      | file   | Share11.txt |
      | folder | Share12     |

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
      | file   | Share13.txt |
      | folder | Share14     |
