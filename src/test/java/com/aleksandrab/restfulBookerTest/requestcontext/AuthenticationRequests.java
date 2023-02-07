package com.aleksandrab.restfulBookerTest.requestcontext;

import com.aleksandrab.restfulBookerTest.PropertiesReader;
import com.aleksandrab.restfulBookerTest.dtos.Auth;
import com.aleksandrab.restfulBookerTest.dtos.EndpointUri;

import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * Class containing methods that wrap requests to Auth API
 */
public class AuthenticationRequests {

    Properties properties = new Properties();
    /**
     * Request to authenticate (create a token)
     *
     * @return generated access token
     */
    public String createToken() {
        PropertiesReader.readDataFromProperties(properties);
        Auth auth = new Auth(properties.getProperty("username"), properties.getProperty("password"));
        return given().header("Content-Type", "application/json")
                .when().body(auth).post(EndpointUri.AUTHENTICATE.getUri())
                .body().jsonPath().get("token");
    }
}
