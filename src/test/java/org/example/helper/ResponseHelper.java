package org.example.helper;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class ResponseHelper {
    public static void assertSuccessfulResponse(Response response) {
        response.then().statusCode(anyOf(is(200), is(201), is(202)));
    }

    public static void storeETag(Response response) {
        String eTag = response.getHeader("etag");
        Serenity.setSessionVariable("eTag").to(eTag);
    }
}
