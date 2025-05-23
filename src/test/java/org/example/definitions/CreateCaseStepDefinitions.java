package org.example.definitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
//import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.Base64;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class CreateCaseStepDefinitions {

    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    private String accessToken;
    private Response response;

    public CreateCaseStepDefinitions() {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
        this.baseUrl = env.getProperty("base.url");
        this.clientId = env.getProperty("client.id");
        this.clientSecret = env.getProperty("client.secret");
        this.username = env.getProperty("username");
        this.password = env.getProperty("password");
    }

    @Given("I have a valid access token")
    public void i_have_a_valid_access_token() {
        RestAssured.useRelaxedHTTPSValidation();

        response = SerenityRest
                .given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(baseUrl + "/prweb/PRRestService/oauth2/v1/token");

        response.then().log().all();

        accessToken = response.jsonPath().getString("access_token");
        Serenity.setSessionVariable("accessToken").to(accessToken);
    }

    @When("I create a new case")
    public void i_create_a_new_case() {
        String payload = """
        {
            "content": {
                "pyDescription": "Description"
            },
            "caseTypeID": "SL-TellUsMore-Work-Incident",
            "processID": "pyStartCase",
            "parentCaseID": ""
        }
        """;

        response = SerenityRest
                .given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + accessToken)
                .header("x-origin-channel", "Web")
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(payload)
                .when()
                .post(baseUrl + "/prweb/api/application/v2/cases?viewType=none");

        response.then().log().all();

        String caseId = response.jsonPath().getString("ID");
        Serenity.setSessionVariable("caseID").to(caseId);
    }

    @Then("the case is created successfully")
    public void case_created_successfully() {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }
}
