package org.example.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import static org.hamcrest.Matchers.equalTo;

    public class EmployeeDefinitions {
        private static final String URL = "http://dummy.restapiexample.com/api/v1/employee/1";
        public Response response;

        @Given("I send a request to endpoint")
        public void sendRequest()  {

            response = SerenityRest.given().contentType("application/json").header("Content-Type", "application/json")
                    .when().get(URL);
        }

        @Then("the API should return status {int}")
        public void verifyResponse(int status) {
            SerenityRest.restAssuredThat(response -> response.statusCode(status));
        }

        @And("Response should contains employee name {string}")
        public void verifyResponseContent(String expectedEmployeeName) {

            SerenityRest.restAssuredThat(response -> response.body("data.employee_name", equalTo(expectedEmployeeName)));
        }
    }


