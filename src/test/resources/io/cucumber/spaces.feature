@spaces @ocis @ignore
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and add share my content with other members

  Background: User is logged in
    Given user Alice is logged

    Rule: List correct spaces

      @smoke
      Scenario: Create a new space with correct name and subtitle
        Given the following spaces have been created in the account
          | Space1    | First space    |
          | Space2    | Second space   |
        When Alice selects the spaces view
        Then Alice should see the following spaces
          | Space1    | First space    |
          | Space2    | Second space   |

      Scenario: Add a new space with correct name and subtitle
        Given the following spaces have been created in the account
          | Space3    | Third space     |
        And Alice selects the spaces view
        When the following spaces have been created in the account
          | Space4    | Fourth space    |
        And Alice refreshes the list
        Then Alice should see the following spaces
          | Space3    | Third space    |
          | Space4    | Fourth space   |

      Scenario: Disable a space
        Given the following spaces have been created in the account
          | Space5    | Fifth space     |
          | Space6    | Sixth space     |
        And Alice selects the spaces view
        When following space is disabled in server
          | Space5    | Fifth space     |
        And Alice refreshes the list
        Then Alice should see the following spaces
          | Space6    | Sixth space     |
        And Alice should not see the following spaces
          | Space5    | Fifth space     |
