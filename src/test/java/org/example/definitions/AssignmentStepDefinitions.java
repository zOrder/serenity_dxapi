package org.example.definitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.example.helper.RequestHelper;
import org.example.helper.ResponseHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        ResponseHelper.storeETag(response);
    }

    @Then("the assignment details are returned")
    public void assignmentDetailsReturned() {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }

    @When("I perform update on the assignment category")
    public void performAssignmentCategoryUpdate() {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        String eTag = Serenity.sessionVariableCalled("eTag");
        String actionId = "DetermineCategory";

        String payload = """
        {
            "content": {
                "IncidentType": "Customer service issue",
                "IncidentSubType": "Complaint not handled as requested"
            }
        }
        """;

        response = RequestHelper.authRequest()
                .header("If-Match", eTag)
                .header("x-origin-channel", "Web")
                .contentType("application/json")
                .accept("application/json")
                .queryParam("viewType", "none")
                .body(payload)
                .log().all()
                .patch(baseUrl + "/prweb/api/application/v2/assignments/" + assignmentId + "/actions/" + actionId);

        ResponseHelper.storeETag(response);
    }


    @When("I perform update on the assignment service details")
    public void performAssignmentServiceUpdate() {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        String eTag = Serenity.sessionVariableCalled("eTag");
        String actionId = "ServiceDetails";

        String payload = """
        {
          "content": {
            "CommunicationChannel": "In person",
            "WhatHappened": "The product arrived damaged and missing components.",
            "WhenDidThisHappen": "2025-05-28"
          }
        }
        """;

        response = RequestHelper.authRequest()
                .header("If-Match", eTag)
                .header("x-origin-channel", "Web")
                .contentType("application/json")
                .accept("application/json")
                .queryParam("viewType", "none")
                .body(payload)
                .log().all()
                .patch(baseUrl + "/prweb/api/application/v2/assignments/" + assignmentId + "/actions/" + actionId);

        ResponseHelper.storeETag(response);
    }

    @When("I perform update on the assignment customer details")
    public void performUpdateOnAssignmentCustomerDetails() throws IOException {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        String eTag = Serenity.sessionVariableCalled("eTag");
        String actionId = "ContactInfo";
        String payloadPath = "src/test/resources/payloads/customer_details_payload.json";
        String payload = Files.readString(Paths.get(payloadPath));

        response = RequestHelper.authRequest()
                .header("If-Match", eTag)
                .header("x-origin-channel", "Web")
                .contentType("application/json")
                .accept("application/json")
                .queryParam("viewType", "none")
                .body(payload)
                .log().all()
                .patch(baseUrl + "/prweb/api/application/v2/assignments/" + assignmentId + "/actions/" + actionId);
        
        ResponseHelper.storeETag(response);
    }

    @When("I select a resolution on the assignment service details")
    public void selectResolutionOnAssignmentServiceDetails() {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        String eTag = Serenity.sessionVariableCalled("eTag");
        String actionId = "ResolutionMethod";

        String payload = """
        {
          "content": {
            "PreferredResolutionMethod": "Replacement"
          },
          "pageInstructions": []
        }
        """;

        response = RequestHelper.authRequest()
                .header("If-Match", eTag)
                .header("x-origin-channel", "Web")
                .contentType("application/json")
                .accept("application/json")
                .queryParam("viewType", "page")
                .body(payload)
                .log().all()
                .patch(baseUrl + "/prweb/api/application/v2/assignments/" + assignmentId + "/actions/" + actionId);

        ResponseHelper.storeETag(response);
    }


    @Then("the assignment details are updated")
    public void assignmentSubmissionReturned() {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }
}
