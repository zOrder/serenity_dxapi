package org.example.definitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.Matchers.*;

public class AssignmentStepDefinitions {

    private Response response;

    @When("I request the assignment details")
    public void getAssignmentDetails() {

        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        System.out.println("---=== assignmentId: " + assignmentId);
        response = SerenityRest
                .given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + SessionContext.getAccessToken())
                .accept("application/json")
                .when()
                .get("/prweb/api/application/v2/assignments/" + assignmentId);
        //TODO double check with postman if url is correct
    }

    @Then("the assignment details are returned")
    public void assignmentDetailsReturned() {
        response.then().statusCode(200).body("actions", notNullValue());
    }

    @When("I request the submit action details for assignment {string} and action {string}")
    public void getSubmitActionDetails(String assignmentId, String actionId) {
        response = SerenityRest
                .given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + SessionContext.getAccessToken())
                .accept("application/json")
                .when()
                .get("/prweb/api/application/v2/assignments/" + assignmentId + "/actions/" + actionId);
    }

    @Then("the submit action metadata is returned")
    public void submitActionDetailsReturned() {
        response.then().statusCode(200).body("fields", notNullValue());
    }

    @When("I perform the submit action {string} on assignment {string} with required data")
    public void performSubmitAction(String actionId, String assignmentId) {
        String payload = "{}";

        response = SerenityRest
                .given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + SessionContext.getAccessToken())
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/prweb/api/application/v2/assignments/" + assignmentId);
    }

    @Then("the assignment is successfully submitted")
    public void assignmentSubmitted() {
        response.then().statusCode(anyOf(is(200), is(201), is(204)));
    }
}
