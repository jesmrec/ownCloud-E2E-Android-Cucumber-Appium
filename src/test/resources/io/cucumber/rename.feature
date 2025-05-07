@rename
Feature: Rename an item

  As a user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user Alice is logged

  Scenario Outline: Rename an item that is not downloaded
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Rename the <type> <name>
    And Alice sets <newName> as new name
    Then Alice should see <newName> in the filelist
    But Alice should not see <name> in the filelist anymore

    Examples:
      | type   | name      | newName     |
      | folder | folder1   | rename1     |
      | file   | file2.txt | rename2.txt |

  @smoke
  Scenario Outline: Rename a file that is downloaded
    Given the following items have been created in Alice account
      | <type> | <name> |
    When Alice selects to Download the <type> <name>
    And Alice closes the preview
    And Alice selects to Rename the <type> <name>
    And Alice sets <newName> as new name
    Then Alice should see <newName> in the filelist
    But Alice should not see <name> in the filelist anymore
    And <type> <newName> should be stored in device
    But <type> <name> should not be stored in device

    Examples:
      | type | name      | newName     |
      | file | file3.txt | rename3.txt |

  Scenario Outline: Rename an item with an existing name
    Given the following items have been created in Alice account
      | <type> | <name>    |
      | <type> | <newName> |
    When Alice selects to Rename the <type> <name>
    And Alice sets <newName> as new name
    Then Alice should see the following error
      | Rename could not be completed |

    Examples:
      | type   | name    | newName |
      | folder | folder4 | folder5 |