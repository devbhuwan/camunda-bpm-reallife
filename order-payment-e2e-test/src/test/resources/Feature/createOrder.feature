Feature: Testing a Order REST API with Event Bus
  Users should be able to submit POST requests to a web service

  Scenario: Create New Order
    Given order hello
    When users create an new order
    Then the server should create an order and return success
