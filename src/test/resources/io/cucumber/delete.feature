@delete
Feature: Delete item

  As a user
  I want to be able to delete content from my account
  so that i can get rid of the files and folders i do not need anymore

  Background: User is logged in
    Given user user1 is logged

  Scenario: Delete an existent folder
    Given the folder deleteMe has been created in the account
    When user selects to Remove the item deleteMe
    And user accepts the deletion
    Then user should not see deleteMe in the filelist anymore
