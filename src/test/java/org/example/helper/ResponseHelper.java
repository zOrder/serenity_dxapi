package org.example.helper;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class ResponseHelper {
    public static void assertSuccessfulResponse(Response response) {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }
}
