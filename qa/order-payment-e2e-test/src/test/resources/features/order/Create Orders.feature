Feature: It is able to create Order records and add to the the database

  Scenario: Should create another Order object
    Given request body from file "order/requests/newOrder.json"
    Given value assertions are case-insensitive
    And content type is "application/json"

    When the client performs POST request on "/order/create"
    Then status code is 201

  Scenario: Server doesn't accept content-types other than JSON
    Given request body from file "order/requests/newOrder.json"
    When the client performs POST request on "/order/create"
    Then status code is 415
