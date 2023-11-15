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
    Then Alice should see create1 in the filelist

  @ignore
  Scenario: Create a new folder with a correct name in non-root
    Given the following items have been created in the account
      | folder | create2 |
    When Alice browses into create2
    And Alice selects the option Create Folder
    And Alice sets create3 as new name
    Then Alice should see create3 in the filelist

  Scenario: Create a new folder with an existing name
    Given the following items have been created in the account
      | folder | create4 |
    When Alice selects the option Create Folder
    And Alice sets create4 as new name
    Then Alice should see the following error
      | The resource you tried to create already exists |