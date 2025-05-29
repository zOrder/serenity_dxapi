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

//        response.then().log().all();
        String eTag = response.getHeader("etag");
        System.out.println("📦 eTag: " + eTag);
        Serenity.setSessionVariable("eTag").to(eTag);
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

//        response.then().log().all();
        //TODO extract getting etag to helper
        eTag = response.getHeader("etag");
        System.out.println("📦 eTag: " + eTag);
        Serenity.setSessionVariable("eTag").to(eTag);
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

        response.then().log().all();


        //TODO extract getting etag to helper
        eTag = response.getHeader("etag");
        System.out.println("📦 eTag: " + eTag);
        Serenity.setSessionVariable("eTag").to(eTag);
    }

    @When("I perform update on the assignment customer details")
    public void performUpdateOnAssignmentCustomerDetails() {
        String assignmentId = Serenity.sessionVariableCalled("assignmentID");
        String eTag = Serenity.sessionVariableCalled("eTag");
        String actionId = "ContactInfo";

        String payload = """
    
{"content":{"Customer":{"Address":{"pyLocation":{"pyLatLon":"49.6205744, 7.192470500000001"},"pyStreetAddress":"Maibachufer","pyCity":"Berlin","pyPostalCode":"10969","pyCountry":"USA","pyStreetAddress2":"12","pyState":"AL"},"FName":"Horstiii","LName":"Antonna,sd","EMail":"adsd@gmai.com","PhoneNumber":"+489873928793","AddressMode":"Manually"}},"pageInstructions":[]}
    
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

        response.then().log().all();
    }

    @Then("the assignment details are updated")
    public void assignmentSubmissionReturned() {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }
}
