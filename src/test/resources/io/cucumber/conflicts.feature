@conflicts
Feature: Conflicts in content

  As a user
  I want to see when a file is conflicted
  So that i can decide which version i want to keep

  Background: User is logged in
    Given user Alice is logged

  Scenario: Detect Conflict

    Given the following items have been created in Alice account
      | file | conflict1.txt |
    When Alice selects to Download the file conflict1.txt
    And Alice closes the preview
    And file conflict1.txt is modified remotely adding "updatedServer"
    And file conflict1.txt is modified locally adding "updatedLocal"
    And Alice refreshes the list
    And Alice selects to Download the file conflict1.txt
    Then Alice should see the conflict dialog