package org.example.definitions;


import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
//import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;

import java.util.Base64;


public class CreateCaseStepDefinitions {

//    private String accessToken;
    private Response response;

//    @Given("I have a valid access token")
//    public void i_have_a_valid_access_token() {
//        response = SerenityRest
//                .given()
//                    .contentType(ContentType.JSON)
//                    .formParam("grant_type", "client_credentials")
//                    .formParam("client_id", System.getProperty("client.id"))
//                    .formParam("client_secret", System.getProperty("client.secret"))
//                .when()
//                    .post(System.getProperty("base.url") + "/prweb/PRRestService/oauth2/v1/token");
//
//        response.then().statusCode(200);
//        accessToken = response.jsonPath().getString("access_token");
//    }




    @Given("I have a valid access token")
    public void i_have_a_valid_access_token() {
        String baseUrl = System.getProperty("base.url");
        String clientId = System.getProperty("client.id");
        String clientSecret = System.getProperty("client.secret");

        baseUrl = "https://qzzoaeeb.pegaacademy.net";
        clientId = "51744799296870571382";
        clientSecret = "0C92DBD52770B18266103F6C5C3C96F2";

        String url = baseUrl + "/prweb/PRRestService/oauth2/v1/token";

        // Debug print
        System.out.println("BASE_URL: " + baseUrl);
        System.out.println("URL: " + url);
        System.out.println("CLIENT_ID: " + clientId);
        System.out.println("CLIENT_SECRET: " + (clientSecret != null ? "***" : "null"));

        String basicAuth = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        System.out.println("Basic Auth: " + basicAuth);
        Response response = SerenityRest
                .given()
//                .relaxedHTTPSValidation() // if needed
                .header("Authorization", "Basic " + basicAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .when()
                .post(baseUrl + "/prweb/PRRestService/oauth2/v1/token");

        response.then().log().all(); // log full response

        // Store token for next steps
        String token = response.jsonPath().getString("access_token");
        Serenity.setSessionVariable("accessToken").to(token);
    }


    @When("I create a new case")
    public void i_create_a_new_case() {
//        String payload = """
//            {
//                "caseTypeID": "GoGo-GoGoRoad-Work-AssistanceRequest",
//                "processID": "pyStartCase",
//                "parentCaseID": "",
//                "content": {}
//            }
//        """;
//
//        response = SerenityRest
//                .given()
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType("application/json")
//                .body(payload)
//                .when()
//                .post(System.getenv("BASE_URL") + "/prweb/api/application/v2/cases?viewType=none");
    }

    @Then("the case is created successfully")
    public void case_created_successfully() {
//        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }
}
