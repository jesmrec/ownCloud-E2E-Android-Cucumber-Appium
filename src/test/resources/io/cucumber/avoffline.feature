@avoffline
Feature: Set items as available offline (downloaded and synced)

  As a user
  I want to set content as available offline
  So that the content will be always downloaded and synced

  Background: User is logged in
    Given user Alice is logged

  @setasavoffline
  Rule: Set as av.offline

    @smoke
    Scenario Outline: Set a file as available offline
      Given the following items have been created in Alice account
        | <type> | <name> |
      When Alice selects to set as av.offline the item <name>
      Then Alice should see the <type> <name> as av.offline
      And <type> <name> should be stored in device

      Examples:
        | type | name        |
        | file | ao1file.pdf |

    Scenario Outline: Set a folder as available offline
      Given the following items have been created in Alice account
        | folder | <folderName>            |
        | file   | <folderName>/<fileName> |
      When Alice selects to set as av.offline the item <folderName>
      And Alice browses into <folderName>
      Then Alice should see the file <fileName> as av.offline
      And file <folderName>/<fileName> should be stored in device

      Examples:
        | folderName | fileName    |
        | ao2folder  | ao2file.txt |

  @moveavoffline
  Rule: Moving av.offline items

    Scenario Outline: Moving an av.offline item to other location does not lose the av.offline condition
      Given the following items have been created in Alice account
        | file   | <fileName>   |
        | folder | <folderName> |
      When Alice selects to set as av.offline the item <fileName>
      And Alice selects to Move the file <fileName>
      And Alice selects <folderName> as target folder
      And Alice browses into <folderName>
      Then Alice should see the file <fileName> as av.offline
      And file <folderName>/<fileName> should be stored in device

      Examples:
        | folderName | fileName    |
        | ao3folder  | ao3file.txt |

    Scenario Outline: Moving a file inside an av.offline folder, turns the file av.offline
      Given the following items have been created in Alice account
        | file   | <fileName>   |
        | folder | <folderName> |
      When Alice selects to set as av.offline the item <folderName>
      And Alice selects to Move the file <fileName>
      And Alice selects <folderName> as target folder
      Then Alice browses into <folderName>
      And Alice should see the item <fileName> as av.offline
      And file <folderName>/<fileName> should be stored in device

      Examples:
        | folderName | fileName    |
        | ao4folder  | ao4file.txt |

    Scenario Outline: Moving a file that is inside an av.offline folder to a non av.offline folder, turns not av.offline
      Given the following items have been created in Alice account
        | folder | <folderName>            |
        | file   | <folderName>/<fileName> |
      When Alice selects to set as av.offline the item <folderName>
      And Alice selects to Move the file <folderName>/<fileName>
      And Alice selects / as target folder
      And Alice browses to root folder
      Then Alice should not see the file <fileName> as av.offline
      And file <fileName> should be stored in device

      Examples:
        | folderName | fileName    |
        | ao5folder  | ao5file.txt |

  @smoke
  Rule: Av.offline file modification

    Scenario Outline: Update over an av.offline file
      Given the following items have been created in Alice account
        | file | <fileName> |
      When Alice selects to set as av.offline the item <fileName>
      And file <fileName> is modified remotely adding "updated"
      And Alice selects to Download the item <fileName>
      Then Alice should see the file <fileName> with "updated"

      Examples:
        | fileName    |
        | ao6file.txt |

  @unsetavoffline
  Rule: Unset as av.offline

    Scenario Outline: Unset a file as available offline
      Given the following items have been created in Alice account
        | file | <fileName> |
      And Alice selects to set as av.offline the item <fileName>
      When Alice selects to unset as av.offline the item <fileName>
      Then Alice should not see the file <fileName> as av.offline
      And file <fileName> should be stored in device

      Examples:
        | fileName    |
        | ao7file.pdf |

    Scenario Outline: Unset a folder as available offline
      Given the following items have been created in Alice account
        | folder | <folderName> |
      And Alice selects to set as av.offline the item <folderName>
      When Alice selects to unset as av.offline the item <folderName>
      Then Alice should not see the folder <folderName> as av.offline

      Examples:
        | folderName |
        | ao8folder  |

    Scenario Outline: Not possible to unset an item as available offline if parent is av. offline
      Given the following items have been created in Alice account
        | folder | <folderName>            |
        | file   | <folderName>/<fileName> |
      When Alice selects to set as av.offline the item <folderName>
      And Alice browses into <folderName>
      Then Alice cannot unset as av.offline the item <fileName>

      Examples:
        | folderName | fileName    |
        | ao9folder  | ao9file.txt |

  @avofflineshortcut
  Rule: Av. offline shortcut

    Scenario Outline: Available offline shortcut
      Given the following items have been created in Alice account
        | file   | <fileName>   |
        | folder | <folderName> |
      And Alice selects to set as av.offline the item <fileName>
      When Alice opens the available offline shortcut
      Then Alice should see "<fileName>" in the list
      But Alice should not see <folderName> in the offline list

      Examples:
        | folderName | fileName     |
        | ao10folder | ao10file.pdf |

  Scenario Outline: Remove from available offline shortcut
        Given the following items have been created in Alice account
          | file | <fileName> |
        And Alice selects to set as av.offline the item <fileName>
        When Alice opens the available offline shortcut
        And Alice selects to unset as av.offline the item <fileName>
        Then Alice should not see <fileName> in the offline list
        And Alice should see the following message
          | No available offline files |

        Examples:
          | fileName     |
          | ao11file.txt |
