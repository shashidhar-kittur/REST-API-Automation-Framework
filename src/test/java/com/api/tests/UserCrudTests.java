package com.api.tests;

import com.api.base.BaseTest;
import com.api.constants.Endpoints;
import com.api.models.UserRequest;
import com.api.models.UserResponse;
import com.api.utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * UserCrudTests
 * 
 * Demonstrates complete automated CRUD (Create, Read, Update, Delete) API testing.
 * - Validates HTTP Status codes (201, 200).
 * - Validates JSON response bodies (POJO deserialization & JsonPath extraction).
 * - Validates Response Headers and Response Time.
 * - Clean, standard code designed for easy explanation during technical interviews.
 */
public class UserCrudTests extends BaseTest {

    private static Integer createdUserId;

    @Test(priority = 1, description = "POST - Create a new user and validate 201 Created")
    public void testCreateUser() {
        ExtentReportManager.log(Status.INFO, "Step 1: Preparing UserRequest POJO payload");
        UserRequest userPayload = new UserRequest("Alex Johnson", "alexj", "alex.johnson@example.com");

        ExtentReportManager.log(Status.INFO, "Step 2: Sending POST request to " + Endpoints.USERS);
        Response response = given()
                .spec(requestSpec)
                .body(userPayload)
        .when()
                .post(Endpoints.USERS)
        .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .time(lessThan(5000L)) // SLA Response time assertion (< 5 seconds)
                .extract().response();

        ExtentReportManager.log(Status.INFO, "Step 3: Deserializing response into UserResponse POJO");
        UserResponse userResponse = response.as(UserResponse.class);

        // Assertions on the response body
        Assert.assertNotNull(userResponse.getId(), "User ID should not be null");
        Assert.assertEquals(userResponse.getName(), userPayload.getName(), "User name should match request");
        Assert.assertEquals(userResponse.getUsername(), userPayload.getUsername(), "Username should match request");
        Assert.assertEquals(userResponse.getEmail(), userPayload.getEmail(), "Email should match request");

        createdUserId = userResponse.getId();
        ExtentReportManager.log(Status.PASS, "Successfully created user with ID: " + createdUserId);
    }

    @Test(priority = 2, description = "GET - Retrieve user by ID and validate 200 OK & response fields")
    public void testGetUserById() {
        int userId = 1;
        ExtentReportManager.log(Status.INFO, "Sending GET request for user ID: " + userId);

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
        .when()
                .get(Endpoints.USER_BY_ID)
        .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract().response();

        // Deserializing response to POJO
        UserResponse user = response.as(UserResponse.class);

        Assert.assertEquals(user.getId(), Integer.valueOf(userId), "Returned user ID should match query");
        Assert.assertEquals(user.getName(), "Leanne Graham", "User name should match expected value");
        Assert.assertEquals(user.getEmail(), "Sincere@april.biz", "Email should match expected value");

        ExtentReportManager.log(Status.PASS, "Verified user details for " + user.getName() + " (" + user.getEmail() + ")");
    }

    @Test(priority = 3, description = "GET - Retrieve posts with query parameters (userId=1)")
    public void testGetPostsWithQueryParams() {
        int targetUserId = 1;
        ExtentReportManager.log(Status.INFO, "Sending GET /posts with query param 'userId=" + targetUserId + "'");

        Response response = given()
                .spec(requestSpec)
                .queryParam("userId", targetUserId)
        .when()
                .get(Endpoints.POSTS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract().response();

        // Extract list of userIds from the JSON array using JsonPath
        List<Integer> userIds = response.jsonPath().getList("userId");
        Assert.assertTrue(userIds.size() > 0, "Posts list should not be empty");

        // Verify all returned posts belong to target user
        for (Integer uid : userIds) {
            Assert.assertEquals(uid, Integer.valueOf(targetUserId), "Post does not belong to expected userId");
        }

        ExtentReportManager.log(Status.PASS, "Retrieved " + userIds.size() + " posts all belonging to userId " + targetUserId);
    }

    @Test(priority = 4, description = "PUT - Full update of user and validate 200 OK")
    public void testUpdateUserWithPut() {
        int userId = 1;
        UserRequest updatePayload = new UserRequest("Alex Johnson", "alexj_senior", "alex.leadqa@example.com");

        ExtentReportManager.log(Status.INFO, "Sending PUT request to update user " + userId);

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .body(updatePayload)
        .when()
                .put(Endpoints.USER_BY_ID)
        .then()
                .statusCode(200)
                .extract().response();

        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getName(), updatePayload.getName());
        Assert.assertEquals(userResponse.getUsername(), updatePayload.getUsername());
        Assert.assertEquals(userResponse.getEmail(), updatePayload.getEmail());

        ExtentReportManager.log(Status.PASS, "Successfully performed full update via PUT for user ID: " + userId);
    }

    @Test(priority = 5, description = "PATCH - Partial update of user email and validate 200 OK")
    public void testPartialUpdateUserWithPatch() {
        int userId = 1;
        // Updating only the email field
        UserRequest patchPayload = new UserRequest();
        patchPayload.setEmail("updated.qa.email@example.com");

        ExtentReportManager.log(Status.INFO, "Sending PATCH request to update email only for user " + userId);

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .body(patchPayload)
        .when()
                .patch(Endpoints.USER_BY_ID)
        .then()
                .statusCode(200)
                .extract().response();

        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getEmail(), "updated.qa.email@example.com");

        ExtentReportManager.log(Status.PASS, "Successfully performed partial update via PATCH for user ID: " + userId);
    }

    @Test(priority = 6, description = "DELETE - Delete user and validate 200 OK")
    public void testDeleteUser() {
        int userId = 1;
        ExtentReportManager.log(Status.INFO, "Sending DELETE request for user " + userId);

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
        .when()
                .delete(Endpoints.USER_BY_ID)
        .then()
                .statusCode(200);

        ExtentReportManager.log(Status.PASS, "Successfully deleted user with ID " + userId + " (Status 200 OK)");
    }
}
