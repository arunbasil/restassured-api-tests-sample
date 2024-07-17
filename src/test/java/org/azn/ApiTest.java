package org.azn;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest extends BaseTest {

    @Test
    public void testVerifyAPISuccess() {
        Response response = given()
                .queryParam("account_number", "12345678")
                .queryParam("payee_name", "Arun Basil")
                .when()
                .get("/verify");

        // Print the response for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        response.then()
                .statusCode(200)
                .body("cop_account_name", equalTo("Arun Basil"))
                .body("cop_outcome", equalTo("MATCH"))
                .body("reason_code", equalTo("PANM"))
                .body("reason_description", equalTo("Personal account, name matches"));
    }

    @Test
    public void testVerifyAPIBadRequest() {
        Response response = given()
                .queryParam("account_number", "invalid")
                .when()
                .get("/verify");

        // Print the response for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        response.then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Invalid account number"));
    }

    @Test
    public void testVerifyAPIInternalServerError() {
        Response response = given()
                .queryParam("account_number", "12345678")
                .queryParam("payee_name", "ServerError")
                .when()
                .get("/verify");

        // Print the response for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        response.then()
                .statusCode(500)
                .body("error", equalTo("Internal Server Error"))
                .body("message", equalTo("An unexpected error occurred"));
    }
}
