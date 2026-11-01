package com.api.base;

import com.api.utils.ConfigReader;
import com.api.utils.SpecBuilder;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest
 * 
 * Parent class for all test classes.
 * - Sets the base URI from configuration once before any test suite runs.
 * - Provides a reusable RequestSpecification to child test classes.
 * - Centralizes setup logic to ensure clean, DRY (Don't Repeat Yourself) code.
 */
public abstract class BaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        // Set RestAssured global baseURI from config.properties
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        
        // Initialize common RequestSpecification with standard headers and logging
        requestSpec = SpecBuilder.getRequestSpec();

        System.out.println(">>> Initialized API Automation Suite with Base URI: " + RestAssured.baseURI);
    }
}
