@booking @partialUpdateBooking
Feature: Update only certain parts of the booking
  As a booking API authenticated user
  I want to be able to update the existing booking
  So that I can have correct information

  Background: User is authenticated and a booking already exists
    Given a user is authenticated
    And there is a booking created

  @smoke @partialUpdateExistingBooking
  Scenario: Update an existing booking with a new name
    When I attempt to update booking with partial data:
      | firstname | lastname |
      | Albert    | Johnson  |
    Then the booking should be successfully updated
    And the booking should contain following data:
      | firstname | lastname |
      | Albert    | Johnson  |

  @partialUpdateExistingBooking
  Scenario Outline: Update an existing booking with partial data
    When I attempt to update booking with partial data:
      | firstname   | lastname   | totalprice   | depositpaid   | additionalneeds   | checkin   | checkout   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <additionalneeds> | <checkin> | <checkout> |
    Then the booking should be successfully updated
    And the booking should contain following data:
      | firstname   | lastname   | totalprice   | depositpaid   | additionalneeds   | checkin   | checkout   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <additionalneeds> | <checkin> | <checkout> |
    Examples:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Pablo     |          | 2000       | false       |                 |            |            |
      |           |          | 2000       |             | Breakfast       |            |            |
      |           |          | 2000       |             |                 |            | 2024-01-01 |
      |           |          |            |             |                 | 2023-01-01 |            |
      | Anna      | Smith    | 230        | true        | Breakfast       | 2023-01-02 | 2023-02-03 |


  @partialUpdateNegativeScenarios @partialUpdateBookingDoesntExist
  Scenario: Updating a booking by id that doesn't exist
    When I attempt to update a booking that doesn't exist
    Then I should receive an error that the method is not allowed

  @partialUpdateNegativeScenarios @partialUpdateBookingWrongInfo
  Scenario: Updating a booking with wrong information
    Given there is a booking created with data:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Anna      | Smith    | 230        | true        | Breakfast       | 2023-01-02 | 2023-02-03 |
    When I attempt to update a booking with wrong information
    Then the booking should be successfully updated
    And the booking should contain following data:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Anna      | Smith    | 230        | true        | Breakfast       | 2023-01-02 | 2023-02-03 |

  @partialUpdateNegativeScenarios @partialUpdateBookingNotAuthenticated
  Scenario: Updating a booking when user is not authenticated
    Given I am not authenticated
    When I attempt to update booking with partial data:
      | firstname | lastname |
      | Albert    | Johnson  |
    Then I should receive an error saying that I am not authenticated

  @partialUpdateNegativeScenarios @partialUpdateBookingEmptyBody
  Scenario: Updating a booking with an empty request
    Given there is a booking created with data:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Anna      | Smith    | 230        | false        | Breakfast       | 2023-01-02 | 2023-02-03 |
    When I attempt to update booking with empty object
    Then the booking should be successfully updated
    And the booking should contain following data:
      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin    | checkout   |
      | Anna      | Smith    | 230        | false        | Breakfast       | 2023-01-02 | 2023-02-03 |

  @partialUpdateNegativeScenarios @partialUpdateBookingIdWrong
  Scenario: Updating a booking by id that is in incorrect format
    When I attempt to update a booking with an id in incorrect format
    Then I should receive an error that the method is not allowed
