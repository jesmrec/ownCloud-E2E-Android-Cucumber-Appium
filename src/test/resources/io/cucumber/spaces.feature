@spaces @nooc10
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and add share my content with other members

  Background: User is logged in
    Given user Alice is logged

  @listspace
  Rule: List correct spaces

    @smoke
    Scenario Outline: List space created in server
      Given the following spaces have been created in Alice account
        | <name1> | <subtitle1> |
        | <name2> | <subtitle2> |
      When Alice selects the spaces view
      Then Alice should see the following spaces
        | <name1> | <subtitle1> |
        | <name2> | <subtitle2> |

      Examples:
        | name1  | subtitle1   | name2  | subtitle2    |
        | Space1 | First space | Space2 | Second space |

    Scenario Outline: Update space created in server
        Given the following spaces have been created in Alice account
          | <name1> | <subtitle1> |
      And Alice selects the spaces view
        When the following spaces have been created in Alice account
          | <name2> | <subtitle2> |
        And Alice refreshes the list
        Then Alice should see the following spaces
          | <name1> | <subtitle1>  |
          | <name2> | <subtitle2> |

    Examples:
      | name1  | subtitle1   | name2  | subtitle2    |
      | Space3 | Third space | Space4 | Fourth space |

  Scenario Outline: Disable a space in the server
      Given the following spaces have been created in Alice account
        | <name1> | <subtitle1> |
        | <name2> | <subtitle2> |
      And Alice selects the spaces view
      When following space is disabled in server
        | <name1> | <subtitle1> |
      And Alice refreshes the list
      Then Alice should see the following spaces
        | <name2> | <subtitle2> |
      But Alice should not see the following spaces
        | <name1> | <subtitle1> |

    Examples:
      | name1  | subtitle1   | name2  | subtitle2   |
      | Space5 | Fifth space | Space5 | Sixth space |


  Scenario Outline: Filter a space
      Given the following spaces have been created in Alice account
        | <name1> | <subtitle1> |
        | <name2> | <subtitle2> |
        | <name3> | <subtitle3> |
    And Alice selects the spaces view
      When Alice filters the list using Space8
      And Alice refreshes the list
      Then Alice should see the following spaces
        | <name2> | <subtitle2> |
      But Alice should not see the following spaces
        | <name1> | <subtitle1> |
        | <name3> | <subtitle3> |

    Examples:
      | name1  | subtitle1     | name2  | subtitle2    | name3  | subtitle3   |
      | Space7 | Seventh space | Space8 | Eighth space | Space9 | Ninth space |

  @createspace
  Rule: Create space (admins, space admins)

    @smoke
    Scenario Outline: Create a new space with correct name, subtitle and quota
      When Alice selects the spaces view
      And Alice creates a new space with the following fields
        | name     | <name>     |
        | subtitle | <subtitle> |
        | quota    | <quota>    |
      Then space should be created in server with the following fields
        | name     | <name>     |
        | subtitle | <subtitle> |
        | quota    | <quota>    |
      Then Alice should see the following spaces
        | <name> | <subtitle> |

      Examples:
        | name    | subtitle       | quota          |
        | Space10 | Tenth space    | 0.0004         |
        | Space11 | Eleventh space | 124.75         |
        | Space12 | Twelfth space  | 1000000        |
        | Space13 | Thirdt space   | No restriction |

  @editspace
  Rule: Edit existing space (admins, space admins)

  Scenario Outline: Edit an existing space with correct name and subtitle
    Given the following spaces have been created in Alice account
      | <name> | <subtitle> |
    When Alice selects the spaces view
    And Alice edits the space <name>
    And Alice updates the space with the following fields
      | name     | <newName>     |
      | subtitle | <newSubtitle> |
      | quota    | <quota>       |
    Then Alice should see the following spaces
      | <newName> | <newSubtitle> |
    But Alice should not see the following spaces
      | <name> | <subtitle> |
    And space should be updated in server with the following fields
      | name     | <newName>     |
      | subtitle | <newSubtitle> |
      | quota    | <quota>       |

    Examples:
      | name    | subtitle         | newName     | newSubtitle      | quota  |
      | Space14 | Fourteenth space | Space14 new | Fourth space new | 100    |
      | Space15 | Fiftenth space   | Space15 new |                  | 125.75 |