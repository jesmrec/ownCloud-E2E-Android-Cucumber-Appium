@shortcuts
Feature: Shortcuts

  As a user
  I want to create a shortcut to any element
  So that, i can jump to any address from my account

  Background: User is logged
    Given user Alice is logged

  @createshortcut
  Rule: Create shortcut

  @smoke
  Scenario Outline: Create correct shortcut over URL
    When Alice selects the option Create Shortcut
    And Alice creates a web shortcut with the following fields
      | <name> | <url> |
    Then Alice should see <name>.url in the filelist
    And Alice should see <name>.url as uploaded in the uploads view

    Examples:
      | name      | url              |
      | shortcut1 | www.owncloud.org |

  @openshortcut
  Rule: Open shortcut

  @smoke
  Scenario Outline: Open shortcut over URL
    Given the following items have been created in Alice account
      | shortcut | <name>.url |
    When Alice opens the shortcut <name>.url
    And Alice opens the link
    Then Alice should see the browser

    Examples:
      | name      |
      | shortcut2 |
