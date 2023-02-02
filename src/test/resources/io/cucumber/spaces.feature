@spaces @ocis @nooc10
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and add share my content with other members

  Background: User is logged in
    Given user Alice is logged

  Scenario: Create a new space with correct name and subtitle
    Given the following spaces have been created in the account
      | Space1    | First space    |
      | Space2    | Second space   |
    When Alice selects the spaces view
    Then Alice should see the following spaces
      | Space1    | First space    |
      | Space2    | Second space   |