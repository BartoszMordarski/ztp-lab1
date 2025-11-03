package com.example.ztp_lab1.steps;

import com.example.ztp_lab1.model.Product;
import com.example.ztp_lab1.model.ProductCategory;
import com.example.ztp_lab1.repository.ProductRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class ProductSteps {

    private final ProductRepository productRepository;
    private Response response;
    private Long currentProductId;
    private Long secondProductId;

    @Given("the application is running")
    public void theApplicationIsRunning() {
    }

    @Given("the database is clean")
    public void theDatabaseIsClean() {
        productRepository.deleteAll();
    }

    @When("I create a product with:")
    public void iCreateProductWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", data.get("name"));
        requestBody.put("price", new BigDecimal(data.get("price")));
        requestBody.put("quantity", Integer.parseInt(data.get("quantity")));
        requestBody.put("category", data.get("category"));

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products");

        if (response.getStatusCode() == 201 && response.jsonPath().get("id") != null) {
            currentProductId = response.jsonPath().getLong("id");
        }
    }

    @When("I create a product without price:")
    public void iCreateProductWithoutPrice(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", data.get("name"));
        requestBody.put("quantity", Integer.parseInt(data.get("quantity")));
        requestBody.put("category", data.get("category"));

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products");
    }

    @When("I create a product without category:")
    public void iCreateProductWithoutCategory(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", data.get("name"));
        requestBody.put("price", new BigDecimal(data.get("price")));
        requestBody.put("quantity", Integer.parseInt(data.get("quantity")));

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products");
    }

    @When("I create a product with invalid category:")
    public void iCreateProductWithInvalidCategory(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", data.get("name"));
        requestBody.put("price", new BigDecimal(data.get("price")));
        requestBody.put("quantity", Integer.parseInt(data.get("quantity")));
        requestBody.put("category", data.get("category"));

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/products");
    }

    @When("I get all products")
    public void iGetAllProducts() {
        response = given()
                .when()
                .get("/api/v1/products");
    }

    @When("I get the product by ID")
    public void iGetProductById() {
        response = given()
                .when()
                .get("/api/v1/products/" + currentProductId);
    }

    @When("I try to get product with ID {long}")
    public void iTryToGetProductWithId(Long id) {
        response = given()
                .when()
                .get("/api/v1/products/" + id);
    }

    @When("I update the product {string} with name {string}")
    public void iUpdateProductWithName(String oldName, String newName) {
        Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals(oldName))
                .findFirst()
                .orElseThrow();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", newName);
        requestBody.put("price", product.getPrice());
        requestBody.put("quantity", product.getQuantity());
        requestBody.put("category", product.getCategory().toString());

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/v1/products/" + product.getId());
    }

    @When("I delete the product")
    public void iDeleteProduct() {
        response = given()
                .when()
                .delete("/api/v1/products/" + currentProductId);
    }

    @When("I try to delete product with ID {long}")
    public void iTryToDeleteProductWithId(Long id) {
        response = given()
                .when()
                .delete("/api/v1/products/" + id);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("the response should contain field {string} with value {string}")
    public void theResponseShouldContainFieldWithValue(String field, String expectedValue) {
        String actualValue = response.jsonPath().getString(field);
        assertThat(actualValue, is(notNullValue()));
        assertThat(actualValue, equalTo(expectedValue));
    }

    @And("the product should exist in the database")
    public void theProductShouldExistInDatabase() {
        assertNotNull(currentProductId);
        assertTrue(productRepository.existsById(currentProductId));
    }

    @And("the product should not exist in the database")
    public void theProductShouldNotExistInDatabase() {
        assertNotNull(currentProductId);
        assertFalse(productRepository.existsById(currentProductId));
    }

    @And("the response should contain {int} products")
    public void theResponseShouldContainProducts(int count) {
        List<?> products = response.jsonPath().getList("$");
        assertEquals(count, products.size());
    }

    @And("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedMessage) {
        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString(expectedMessage));
    }

    @Given("the following products exist:")
    public void theFollowingProductsExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            Product product = Product.builder()
                    .name(row.get("name"))
                    .price(new BigDecimal(row.get("price")))
                    .quantity(Integer.parseInt(row.get("quantity")))
                    .category(ProductCategory.valueOf(row.get("category")))
                    .build();
            productRepository.save(product);
        }
    }

    @Given("a product exists with name {string}")
    public void aProductExistsWithName(String name) {
        Product product = Product.builder()
                .name(name)
                .price(new BigDecimal("2999.99"))
                .quantity(10)
                .category(ProductCategory.ELECTRONICS)
                .build();
        product = productRepository.save(product);

        if (currentProductId == null) {
            currentProductId = product.getId();
        } else {
            secondProductId = product.getId();
        }
    }

    @Given("a product exists with:")
    public void aProductExistsWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        Product product = Product.builder()
                .name(data.get("name"))
                .price(new BigDecimal(data.get("price")))
                .quantity(Integer.parseInt(data.get("quantity")))
                .category(ProductCategory.valueOf(data.get("category")))
                .build();
        product = productRepository.save(product);
        currentProductId = product.getId();
    }
}