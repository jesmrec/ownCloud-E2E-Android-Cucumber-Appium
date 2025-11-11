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
      Then Alice should see the following enabled spaces
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
        Then Alice should see the following enabled spaces
          | <name1> | <subtitle1>  |
          | <name2> | <subtitle2> |

    Examples:
      | name1  | subtitle1   | name2  | subtitle2    |
      | Space3 | Third space | Space4 | Fourth space |

    @ignore
    Scenario Outline: Disable a space in the server
        Given the following spaces have been created in Alice account
          | <name1> | <subtitle1> |
          | <name2> | <subtitle2> |
        And Alice selects the spaces view
        When following space is disabled in server
          | <name1> | <subtitle1> |
        And Alice refreshes the list
        Then Alice should see the following enabled spaces
          | <name2> | <subtitle2> |
        But Alice should see the following disabled spaces
          | <name1> | <subtitle1> |

      Examples:
        | name1  | subtitle1   | name2  | subtitle2   |
        | Space5 | Fifth space | Space6 | Sixth space |

    Scenario Outline: Filter a space
      Given the following spaces have been created in Alice account
        | <name1> | <subtitle1> |
        | <name2> | <subtitle2> |
        | <name3> | <subtitle3> |
      And Alice selects the spaces view
      When Alice filters the list using Space8
      And Alice refreshes the list
      Then Alice should see the following enabled spaces
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
        | unit     | <unit>     |
      Then Alice should see the following enabled spaces
        | <name> | <subtitle> |

      Examples:
        | name    | subtitle       | quota          | unit |
        | Space10 | Tenth space    | 0.0004         | GB   |
        | Space11 | Eleventh space | 124.75         | GB   |
        | Space12 | Twelfth space  | 1000000        | GB   |
        | Space13 | Thirdt space   | No restriction | GB   |

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
      Then Alice should see the following enabled spaces
        | <newName> | <newSubtitle> |
      But Alice should not see the following spaces
        | <name> | <subtitle> |
      And space should be updated in server with the following fields
        | name     | <newName>     |
        | subtitle | <newSubtitle> |
        | quota    | <quota>       |
        | unit     | <unit>        |

      Examples:
        | name    | subtitle         | newName     | newSubtitle      | quota  | unit |
        | Space14 | Fourteenth space | Space14 new | Fourth space new | 100    | GB   |
        | Space15 | Fifteenth space  | Space15 new |                  | 125.75 | GB   |

    Scenario Outline: Edit an existing space with quota only
      Given the following spaces have been created in Alice account
        | <name> | <subtitle> |
      When Alice selects the spaces view
      And Alice edits the space <name>
      And Alice updates the space with the following fields
        | name     | <name>     |
        | subtitle | <subtitle> |
        | quota    | <quota>    |
      Then space should be updated in server with the following fields
        | name     | <name>     |
        | subtitle | <subtitle> |
        | quota    | <newQuota> |
        | unit     | <unit>     |
        And quota is correctly displayed
        | <newQuota> | <unit> | <name> |

      Examples:
        | name    | subtitle          | quota | newQuota | unit |
        | Space16 | Sixteenth space   | 2300  | 2.3      | TB   |
        | Space17 | Seventeenth space | 0.01  | 10       | MB   |

    @ignore
    Scenario Outline: Edit an existing space with new image
        Given the following spaces have been created in Alice account
          | <name> | <subtitle> |
        And a file <fileName> exists in the device
        When Alice selects the spaces view
        And Alice edits the image of the space <name> with the file <fileName>
        Then space image should be updated in server with file <fileName>
          | <name> | <subtitle> |

        Examples:
          | name    | subtitle         | fileName |
          | Space18 | Eighteenth space | icon.png |

  @disablespace  @ignore
  Rule: Disable/Delete existing space (admins, space admins)

    Scenario Outline: Disable an existing space
      Given the following spaces have been created in Alice account
        | <name> | <subtitle> |
      When Alice selects the spaces view
      And Alice disables the space <name>
      Then Alice should see the following disabled spaces
        | <name> | <subtitle> |

      Examples:
        | name    | subtitle         |
        | Space19 | Nineteenth space |

    Scenario Outline: Enable a disabled space
        Given the following spaces have been created in Alice account
          | <name> | <subtitle> |
        And following space is disabled in server
          | <name> | <subtitle> |
        When Alice selects the spaces view
        And Alice enables the space <name>
        Then Alice should see the following enabled spaces
          | <name> | <subtitle> |

        Examples:
          | name    | subtitle        |
          | Space20 | Twentieth space |

    Scenario Outline: Delete a disabled space
        Given the following spaces have been created in Alice account
          | <name> | <subtitle> |
        And following space is disabled in server
          | <name> | <subtitle> |
        When Alice selects the spaces view
        And Alice deletes the space <name>
        Then Alice should not see the following spaces
          | <name> | <subtitle> |

        Examples:
          | name    | subtitle          |
          | Space21 | Twentifirst space |
