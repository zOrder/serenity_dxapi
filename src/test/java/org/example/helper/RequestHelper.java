package org.example.helper;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

public class RequestHelper {
    public static RequestSpecification authRequest() {
        return SerenityRest.given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + SessionContext.getAccessToken())
                .accept("application/json");
    }
}