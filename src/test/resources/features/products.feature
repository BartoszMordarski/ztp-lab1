Feature: Product Management
  As a user
  I want to manage products
  So that I can perform CRUD operations and validate product data

  Background:
    Given the application is running
    And the database is clean

  Scenario: Create a new product successfully
    When I create a product with:
      | name     | Laptop       |
      | price    | 2999.99      |
      | quantity | 10           |
      | category | ELECTRONICS  |
    Then the response status should be 201
    And the response should contain field "name" with value "Laptop"
    And the response should contain field "price" with value "2999.99"
    And the response should contain field "quantity" with value "10"
    And the response should contain field "category" with value "ELECTRONICS"
    And the product should exist in the database

  Scenario: Get all products
    Given the following products exist:
      | name    | price   | quantity | category    |
      | Laptop  | 2999.99 | 10       | ELECTRONICS |
      | Book    | 29.99   | 50       | BOOKS       |
      | Shirt   | 49.99   | 20       | CLOTHES     |
    When I get all products
    Then the response status should be 200
    And the response should contain 3 products

  Scenario: Get product by ID
    Given a product exists with name "Laptop"
    When I get the product by ID
    Then the response status should be 200
    And the response should contain field "name" with value "Laptop"

  Scenario: Get non-existing product returns 404
    When I try to get product with ID 99999
    Then the response status should be 404

  Scenario: Delete a product successfully
    Given a product exists with name "Laptop"
    When I delete the product
    Then the response status should be 204
    And the product should not exist in the database

  Scenario: Delete non-existing product returns 404
    When I try to delete product with ID 99999
    Then the response status should be 404

  Scenario: Product name must be at least 3 characters
    When I create a product with:
      | name     | AB          |
      | price    | 2999.99     |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "must be between 3 and 20 characters"

  Scenario: Product name must be at most 20 characters
    When I create a product with:
      | name     | ThisIsAVeryLongProductName |
      | price    | 2999.99                    |
      | quantity | 10                         |
      | category | ELECTRONICS                |
    Then the response status should be 400
    And the error message should contain "must be between 3 and 20 characters"

  Scenario: Product name can only contain letters and numbers
    When I create a product with:
      | name     | Laptop@123  |
      | price    | 2999.99     |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "can only contain letters and numbers"

  Scenario: Product name cannot be blank
    When I create a product with:
      | name     |             |
      | price    | 2999.99     |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400

  Scenario: Product price must be greater than 0
    When I create a product with:
      | name     | Laptop      |
      | price    | 0           |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "must be greater than 0"

  Scenario: Product price cannot be null
    When I create a product without price:
      | name     | Laptop      |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400

  Scenario: Product quantity cannot be negative
    When I create a product with:
      | name     | Laptop      |
      | price    | 2999.99     |
      | quantity | -5          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "cannot be negative"

  Scenario: Product quantity can be zero
    When I create a product with:
      | name     | Laptop      |
      | price    | 2999.99     |
      | quantity | 0           |
      | category | ELECTRONICS |
    Then the response status should be 201

  Scenario: Product category cannot be null
    When I create a product without category:
      | name     | Laptop  |
      | price    | 2999.99 |
      | quantity | 10      |
    Then the response status should be 400

  Scenario: Electronics price must be between 50 and 50000
    When I create a product with:
      | name     | Laptop      |
      | price    | 25.00       |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "must be between 50 and 50000"

  Scenario: Books price must be between 5 and 500
    When I create a product with:
      | name     | Book        |
      | price    | 600.00      |
      | quantity | 10          |
      | category | BOOKS       |
    Then the response status should be 400
    And the error message should contain "must be between 5 and 500"

  Scenario: Clothes price must be between 10 and 5000
    When I create a product with:
      | name     | Shirt       |
      | price    | 5.00        |
      | quantity | 10          |
      | category | CLOTHES     |
    Then the response status should be 400
    And the error message should contain "must be between 10 and 5000"

  Scenario: Valid electronics price
    When I create a product with:
      | name     | Laptop      |
      | price    | 2999.99     |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 201

  Scenario: Valid books price
    When I create a product with:
      | name     | Book123     |
      | price    | 29.99       |
      | quantity | 50          |
      | category | BOOKS       |
    Then the response status should be 201

  Scenario: Valid clothes price
    When I create a product with:
      | name     | Shirt123    |
      | price    | 49.99       |
      | quantity | 20          |
      | category | CLOTHES     |
    Then the response status should be 201

  Scenario: Cannot create product with duplicate name
    Given a product exists with name "Laptop"
    When I create a product with:
      | name     | Laptop      |
      | price    | 2999.99     |
      | quantity | 10          |
      | category | ELECTRONICS |
    Then the response status should be 400
    And the error message should contain "already exists"

  Scenario: Cannot update product to duplicate name
    Given a product exists with name "Laptop"
    And a product exists with name "Mouse"
    When I update the product "Mouse" with name "Laptop"
    Then the response status should be 400
    And the error message should contain "already exists"


  Scenario: Invalid category is rejected
    When I create a product with invalid category:
      | name     | Laptop  |
      | price    | 2999.99 |
      | quantity | 10      |
      | category | TOYS    |
    Then the response status should be 400
    And the error message should contain "not one of the values accepted for Enum class"