package com.api.tests;

import com.api.base.BaseTest;
import com.api.constants.Endpoints;
import com.api.utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * NegativeTests
 * 
 * Validates error codes, missing resources, and invalid endpoint handling.
 * Demonstrates to interviewers that the framework rigorously covers negative testing:
 * - 404 Not Found for non-existent user ID
 * - 404 Not Found for non-existent post ID
 * - 404 Not Found for undefined route
 * - 404 Not Found on POST to invalid endpoint
 */
public class NegativeTests extends BaseTest {

    @Test(priority = 1, description = "GET - Non-existent user ID returns 404 Not Found")
    public void testGetUser_NotFound_404() {
        int nonExistentUserId = 999;
        ExtentReportManager.log(Status.INFO, "Requesting non-existent user ID: " + nonExistentUserId);

        given()
                .spec(requestSpec)
                .pathParam("id", nonExistentUserId)
        .when()
                .get(Endpoints.USER_BY_ID)
        .then()
                .statusCode(404);

        ExtentReportManager.log(Status.PASS, "Verified 404 Not Found for user ID: " + nonExistentUserId);
    }

    @Test(priority = 2, description = "GET - Non-existent post ID returns 404 Not Found")
    public void testGetPost_NotFound_404() {
        int nonExistentPostId = 99999;
        ExtentReportManager.log(Status.INFO, "Requesting non-existent post ID: " + nonExistentPostId);

        given()
                .spec(requestSpec)
                .pathParam("id", nonExistentPostId)
        .when()
                .get(Endpoints.POST_BY_ID)
        .then()
                .statusCode(404);

        ExtentReportManager.log(Status.PASS, "Verified 404 Not Found for post ID: " + nonExistentPostId);
    }

    @Test(priority = 3, description = "GET - Calling invalid endpoint returns 404 Not Found")
    public void testInvalidEndpoint_404() {
        ExtentReportManager.log(Status.INFO, "Requesting invalid route: " + Endpoints.INVALID_ENDPOINT);

        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.INVALID_ENDPOINT)
        .then()
                .statusCode(404);

        ExtentReportManager.log(Status.PASS, "Verified 404 Not Found for invalid endpoint");
    }

    @Test(priority = 4, description = "POST - Submitting to non-existent endpoint returns 404 Not Found")
    public void testPostToInvalidEndpoint_404() {
        ExtentReportManager.log(Status.INFO, "Sending POST request to non-existent route");

        given()
                .spec(requestSpec)
                .body("{\"test\":\"data\"}")
        .when()
                .post(Endpoints.INVALID_ENDPOINT)
        .then()
                .statusCode(404);

        ExtentReportManager.log(Status.PASS, "Verified 404 Not Found when posting to non-existent endpoint");
    }
}
