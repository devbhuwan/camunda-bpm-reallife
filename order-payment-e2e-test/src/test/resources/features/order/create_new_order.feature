#JIRA:
Feature: Create Order
  User create order

  Background: An Order Service

    Given an order service is available

  Scenario: Create New Order
    When I create a new order
    Then the response is Successfully created your an order
    Given Order Data Entry Form
    When Submit Data Entry Form itemName is Sandwich and quantity is 50
    Then Order is created as a draft
    When Retrieve Order Payment

