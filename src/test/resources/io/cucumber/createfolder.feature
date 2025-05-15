@createfolder
Feature: Create a new folder

  As a user
  I want to create new folders in my account,
  so that i will put order in my file structure

  Background: User is logged in
    Given user Alice is logged

  @smoke
  Scenario: Create a new folder with a correct name in root
    When Alice selects the option Create Folder
    And Alice sets create1 as new name
    Then Alice should see "create1" in the filelist

  Scenario Outline: Create a new folder with a correct name in non-root
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice browses into <name>
    And Alice selects the option Create Folder
    And Alice sets <newName> as new name
    Then Alice should see "<name>/<newName>" in the filelist

    Examples:
      | type   | name    | newName |
      | folder | create2 | create3 |

  Scenario: Create a new folder with an existing name
    Given the following items have been created in Alice account
      | folder | create4 |
    When Alice selects the option Create Folder
    And Alice sets create4 as new name
    Then Alice should see the following error
      | The resource you tried to create already exists |

  @offline
  Scenario: Create a folder with no connection
    When the device has no connection
    And Alice selects the option Create Folder
    And Alice sets create2 as new name
    Then Alice should see the following error
      | Device is not connected to a network |