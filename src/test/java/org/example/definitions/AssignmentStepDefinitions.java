package org.example.definitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.example.helper.RequestHelper;
import org.example.helper.ResponseHelper;

import static org.hamcrest.Matchers.*;

public class AssignmentStepDefinitions {

    private Response response;
    private final String baseUrl;

    public AssignmentStepDefinitions() {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
        this.baseUrl = env.getProperty("base.url");
    }

    @When("I request the assignment details")
    public void getAssignmentDetails() {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");

        response = RequestHelper.authRequest()
                .queryParam("viewType", "page")
                .queryParam("pageName", "")
                .get(baseUrl +"/prweb/api/application/v2/assignments/" + assignmentId);

        response.then().log().all();
    }

    @Then("the assignment details are returned")
    public void assignmentDetailsReturned() {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }

    @When("I request the submit action details for assignment {string} and action {string}")
    public void getSubmitActionDetails(String assignmentId, String actionId) {
        response = RequestHelper.authRequest()
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

        response = RequestHelper.authRequest()
                .body(payload)
                .when()
                .post("/prweb/api/application/v2/assignments/" + assignmentId);
    }

    @Then("the assignment is successfully submitted")
    public void assignmentSubmitted() {
        ResponseHelper.assertSuccessfulResponse(response);
    }
}
