@booking @deleteBooking
Feature: Delete the booking
  As a booking API authenticated user
  I want to be able to delete the existing booking
  So that I can have correct information

  Background: User is authenticated and a booking already exists
    Given a user is authenticated
    And there is a booking created

  @smoke @deleteExistingBooking
  Scenario: Delete an existing booking
    When I attempt to delete an existing booking
    Then the booking should be successfully deleted
    And retrieving it should result in not found response

  @deleteBookingNegativeScenarios @deleteBookingDoesntExist
  Scenario: Deleting a booking by id that doesn't exist
    When I attempt to delete a booking that doesn't exist
    Then I should receive an error that the method is not allowed

  @deleteBookingNegativeScenarios @deleteBookingNotAuthenticated
  Scenario: Deleting a booking when user is not authenticated
    Given I am not authenticated
    When I attempt to delete an existing booking
    Then I should receive an error saying that I am not authenticated

  @deleteBookingNegativeScenarios @deleteBookingIdWrong
  Scenario: Deleting a booking by id that is in incorrect format
    When I attempt to delete a booking with an id in incorrect format
    Then I should receive an error that the method is not allowed

