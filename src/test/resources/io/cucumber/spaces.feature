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
    Scenario: List space created in server
      Given the following spaces have been created in Alice account
        | Space1 | First space  |
        | Space2 | Second space |
      When Alice selects the spaces view
      Then Alice should see the following spaces
        | Space1 | First space  |
        | Space2 | Second space |

    Scenario: Update space created in server
      Given the following spaces have been created in Alice account
        | Space3 | Third space |
      And Alice selects the spaces view
      When the following spaces have been created in Alice account
        | Space4 | Fourth space |
      And Alice refreshes the list
      Then Alice should see the following spaces
        | Space3 | Third space  |
        | Space4 | Fourth space |

    Scenario: Disable a space in the server
      Given the following spaces have been created in Alice account
        | Space5 | Fifth space |
        | Space6 | Sixth space |
      And Alice selects the spaces view
      When following space is disabled in server
        | Space5 | Fifth space |
      And Alice refreshes the list
      Then Alice should see the following spaces
        | Space6 | Sixth space |
      But Alice should not see the following spaces
        | Space5 | Fifth space |

    Scenario: Filter a space
      Given the following spaces have been created in Alice account
        | Space7 | Seventh space |
        | Space8 | Eighth space  |
        | Space9 | Ninth space   |
      And Alice selects the spaces view
      When Alice filters the list using Space8
      And Alice refreshes the list
      Then Alice should see the following spaces
        | Space8 | Eighth space |
      But Alice should not see the following spaces
        | Space7 | Seventh space |
        | Space9 | Ninth space   |

  @createspace
  Rule: Create space

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
      Then Alice should see the following space in the list
        | <name> | <subtitle> |

      Examples:
        | name    | subtitle       | quota          |
        | Space10 | Tenth space    | 1 GB           |
        | Space11 | Eleventh space | No restriction |