package com.api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

/**
 * SpecBuilder
 * 
 * Demonstrates reusable RequestSpecification and ResponseSpecification patterns.
 * In interviews, this proves that you don't repeat common headers, base URLs,
 * logging filters, and common assertions in every single test method.
 */
public class SpecBuilder {

    /**
     * Common RequestSpecification for all standard JSON API calls.
     * Sets base URI, Content-Type, Accept header, and logs details if validation fails.
     */
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }

    /**
     * Common ResponseSpecification verifying status code and maximum response time.
     * 
     * @param expectedStatusCode HTTP status code expected (e.g. 200, 201, 404)
     */
    public static ResponseSpecification getResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectResponseTime(Matchers.lessThan(5000L)) // Verifies API responds within 5 seconds
                .build();
    }

    /**
     * Default ResponseSpecification for successful 200 OK responses.
     */
    public static ResponseSpecification getOkResponseSpec() {
        return getResponseSpec(200);
    }
}
