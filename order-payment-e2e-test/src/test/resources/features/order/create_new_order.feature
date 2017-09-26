#JIRA:
Feature: Create New Order
  User create new order

  Background: An Order Service

    Given an order service is available

  Scenario: Create New Order
    When I create a new order
    Then the response is Successfully created your an order