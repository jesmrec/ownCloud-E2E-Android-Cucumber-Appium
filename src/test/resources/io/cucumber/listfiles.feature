@list
Feature: List of files is correctly retrieved from server.

  As a user
  I want to see in my device the correct list of files in every location
  So that i know which files and folders i have stored in my server

  Background: User is logged in
    Given user user1 is logged

  Scenario Outline: Check items in the list of files of an specific folder
    Then the list of files in <path> folder should match with the server

    Examples:
      | path    |
      | /       |
      | /Photos |
      | /Many   |
