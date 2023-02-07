@booking @getBookingIds
Feature: Retrieve a list of all bookings
  As a booking API user
  I want to be able to retrieve the list of all created bookings
  So that I can have a clear overview

  @getAllBookingIds @smoke
  Scenario: Retrieve list of all existing bookings
    When I attempt to list all available bookings
    Then I should get a list of bookings containing correct information

  @getBookingIdsByFilter @getBookingIdsByName
  Scenario Outline: Retrieve list of bookings filtered by name
    Given there is a booking created with name "<firstname>" "<lastname>"
    When I attempt to filter bookings by name "<firstname>" "<lastname>"
    Then I should get a list of bookings containing correct information
    Examples:
      | firstname | lastname |
      | Anna      | Smith    |
      | Tom       |          |
      |           | Williams |

  @getBookingIdsByFilter @getBookingIdsByDate
  Scenario Outline: Retrieve list of bookings filtered by date
    Given there is a booking created with date "<checkin>" "<checkout>"
    When I attempt to filter bookings by date "<checkin>" "<checkout>"
    Then I should get a list of bookings containing correct information
    Examples:
      | checkin    | checkout   |
      | 2022-01-01 | 2023-01-01 |
      | 2022-01-01 |            |
      |            | 2023-01-01 |

    # filter by checkin doesn't seem to work well, so some tests fail
  @getBookingIdsByFilter
  Scenario Outline: Retrieve list of bookings that match the filter criteria
    Given there is a booking created with data:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Anna      | Smith    | 230        | true        | Breakfast       | 2023-01-02 | 2023-02-03 |
    When I attempt to filter bookings by "<firstname>" "<lastname>" "<checkin>" "<checkout>"
    Then I should get a list of bookings containing correct information
    Examples:
      | firstname | lastname | checkin    | checkout   |
      | Anna      | Smith    | 2023-01-02 | 2023-02-03 |
      |           | Smith    | 2023-01-02 |            |
      | Anna      |          |            | 2023-02-03 |

  @getBookingIdsEmpty
  Scenario: Retrieve empty list of bookings
    When I attempt to filter bookings by "SomeNonExistingName" "SameHere" "1922-12-12" "2442-01-01"
    Then I should get an empty list