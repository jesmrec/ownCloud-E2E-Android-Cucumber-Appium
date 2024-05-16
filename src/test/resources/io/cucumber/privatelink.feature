@privatelink @noocis
Feature: Private Links

  As a user
  I want to open private links inside the app
  so that i will reach directly the referenced content

  Background: User is logged in
    Given user Alice is logged

  Scenario Outline: Item non previewable in non root folder
    Given the following items have been created in Alice account
      | folder | privlink |
      | <type> | <path>   |
    When Alice opens a private link pointing to <path> with scheme owncloud
    Then <type> <name> is opened in the app

    Examples:
      | type   | path                      | name             |
      | file   | privlink/privateLink1.pdf | privateLink1.pdf |
      | folder | privlink/privateLink2     | privateLink2     |

  Scenario Outline: Previewable file in non root folder
    Given the following items have been created in Alice account
      | <type> | <path> |
    When Alice opens a private link pointing to <path> with scheme owncloud
    Then Alice should see the file <name> with textExample

    Examples:
      | type | path             | name             |
      | file | privateLink3.txt | privateLink3.txt |

