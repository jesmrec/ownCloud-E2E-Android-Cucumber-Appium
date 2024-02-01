@links
Feature: Public Links

  As an user
  I want to handle links on my files or folders
  So that the content is accessible for whom i send the link

  Background: User is logged in
    Given user Alice is logged

    @createlink
    Rule: Create a public link

      @smoke
      Scenario Outline: Create a public link with name
        Given the following items have been created in the account
          | <type>   | <item>  |
        When Alice selects to share the <type> <item>
        And Alice creates link on <type> <item> with the following fields
          | name     | <name>   |
          | password | aa55AA.. |
        Then link should be created on <item> with the following fields
          | name | <name> |

        Examples:
          | type   | item       | name  |
          | folder | Links1     | link1 |
          | file   | Links2.txt | link2 |

      @ignore
      Scenario Outline: Create a public link with password
        Given the following items have been created in the account
          | <type>   | <item>  |
        When Alice selects to share the <type> <item>
        And Alice creates link on <type> <item> with the following fields
          | name     | <name>     |
          | password | <password> |
        Then link should be created on <item> with the following fields
          | name     | <name>     |
          | password | <password> |

        Examples:
          | type   | item       | name  | password |
          | folder | Links3     | link3 | aa55AA.. |
          | file   | Links4.txt | link4 | aa55AA.. |

      @nooc10
      Scenario Outline: Create a public link with expiration date
        Given the following items have been created in the account
          | <type>   | <item>  |
        When Alice selects to share the <type> <item>
        And Alice creates link on <type> <item> with the following fields
          | name            | <name>       |
          | expiration days | <expiration> |
          | password        | aa55AA..     |
        Then link should be created on <item> with the following fields
          | name            | <name>        |
          | expiration days | <expiration>  |

        Examples:
          |  type    |  item         |  name    | expiration    |
          |  folder  |  Links5       |  link5   |    7          |
          |  file    |  Links6.txt   |  link6   |    17         |

      Scenario Outline: Create a public link with permissions on a folder
        Given the following items have been created in the account
          | folder   | <item>  |
        When Alice selects to share the folder <item>
        And Alice creates link on folder <item> with the following fields
          | name       | <name>        |
          | permission | <permissions> |
          | password   | aa55AA..      |
        Then link should be created on <item> with the following fields
          | name       | <name>        |
          | permission | <permissions> |

        Examples:
          |  item         |  name    | permissions | description
          |  Links7       |  link7   |    15       | Download / View / Upload
          |  Links8       |  link8   |     4       | Upload Only (File drop)
          |  Links9       |  link9   |     1       | Download / View


    @editlink
    Rule: Edit a public link

      @noocis
      Scenario Outline: Edit existing share on a folder, changing permissions
        Given the following items have been created in the account
          | folder  | <item>  |
        And Alice has shared the folder <item> by link
        When Alice selects to share the folder <item>
        And Alice edits the link on <item> with the following fields
          | permissions | <permissions> |
          | name        | <name>        |
        Then link should be created on <item> with the following fields
          | permissions | <permissions> |
          | name        | <name>        |

        Examples:
          |  item     |  name    | permissions | description
          |  Links10  |  link10  |     15      | Download / View / Upload
          |  Links11  |  link11  |     4       | Upload Only (File drop)
          |  Links12  |  link12  |     1       | Download / View

      @nooc10 @ignore
      Scenario: Edit existing share on a folder, adding password and expiration
        Given the following items have been created in the account
          | folder | Links13 |
          And Alice has shared the folder Links13 by link
        When Alice selects to share the folder Links13
        And Alice edits the link on Links13 with the following fields
          | password        | aa55AA.. |
          | expiration days | 1        |
        Then link should be created on Links13 with the following fields
          | password        | aa55AA.. |
          | expiration days | 1        |


    @deletelink
    Rule: Delete a public link

      Scenario Outline: Delete existing link
        Given the following items have been created in the account
          | <type>   | <item>  |
        And Alice has shared the <type> <item> by link
        When Alice selects to share the <type> <item>
        And Alice deletes the link on <item>
        Then link on <item> should not exist anymore

        Examples:
          | type   | item        |
          | folder | Links14     |
          | file   | Links15.txt |

    @linkshortcut @noocis
    Rule: Public link Shortcut

      Scenario: Public link shortcut shows correct links
        Given the following items have been created in the account
          | file   | Links16.txt |
          | file   | Links17.txt |
          | folder | Links18     |
          | folder | Links19     |
        And Alice has shared the file Links16.txt by link
        And Alice has shared the folder Links18 by link
        When Alice opens the public link shortcut
        Then Alice should see Links16.txt in the list
        And Alice should see Links18 in the list
        But Alice should not see Links17.txt in the links list
        And Alice should not see Links19 in the links list

      Scenario: Remove from available offline shortcut
        Given the following items have been created in the account
          | file | Links20.txt |
        And Alice has shared the file Links20.txt by link
        When Alice opens the public link shortcut
        And Alice selects to share the file Links20.txt
        And Alice deletes the link on Links20.txt
        And Alice closes share view
        And Alice refreshes the list
        Then Alice should not see Links20.txt in the offline list
        And Alice should see the following message
          | No shared links |