package org.azn;

import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BaseTest {

    private static WireMockServer wireMockServer;

    @BeforeClass
    public void setup() {
        // Start WireMock server on port 8080
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();

        // Set up stub for the /verify endpoint - Success response
        wireMockServer.stubFor(get(urlPathEqualTo("/verify"))
                .withQueryParam("account_number", equalTo("12345678"))
                .withQueryParam("payee_name", equalTo("John Smith"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"cop_account_name\": \"John Smith\", \"cop_outcome\": \"MATCH\", \"reason_code\": \"PANM\", \"reason_description\": \"Personal account, name matches\"}")));

        // Set up stub for the /verify endpoint - 400 Bad Request response
        wireMockServer.stubFor(get(urlPathEqualTo("/verify"))
                .withQueryParam("account_number", equalTo("invalid"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Bad Request\", \"message\": \"Invalid account number\"}")));

        // Set up stub for the /verify endpoint - 500 Internal Server Error response
        wireMockServer.stubFor(get(urlPathEqualTo("/verify"))
                .withQueryParam("account_number", equalTo("12345678"))
                .withQueryParam("payee_name", equalTo("ServerError"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Internal Server Error\", \"message\": \"An unexpected error occurred\"}")));

        // Set the base URI for RestAssured to the WireMock server
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
    }

    @AfterClass
    public void teardown() {
        // Stop WireMock server
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}