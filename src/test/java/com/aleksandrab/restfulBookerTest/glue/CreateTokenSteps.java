package com.aleksandrab.restfulBookerTest.glue;

import com.aleksandrab.restfulBookerTest.dtos.TestContext;
import com.aleksandrab.restfulBookerTest.requestcontext.AuthenticationRequests;
import io.cucumber.java.en.Given;

/**
 * Glue methods for steps to authenticate
 */
public class CreateTokenSteps {
    private TestContext testContext;

    private AuthenticationRequests authenticationRequests = new AuthenticationRequests();

    /**
     * Using DI to store values in variables and reuse them in different steps and classes
     */
    public CreateTokenSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Request to log in and generate access token and set it to testContext
     */
    @Given("a user is authenticated")
    public void aUserIsAuthenticated() {
        testContext.setToken(authenticationRequests.createToken());
    }

    @Given("I am not authenticated")
    public void iAmNotAuthenticated() {
        testContext.setToken("");
    }
}
