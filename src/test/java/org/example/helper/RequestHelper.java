package org.example.helper;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.example.definitions.SessionContext;

public class RequestHelper {
    public static RequestSpecification authRequest() {
        return SerenityRest.given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + SessionContext.getAccessToken())
                .accept("application/json");
    }
}