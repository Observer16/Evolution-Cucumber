package impl;

import config.TestConfig;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import static context.RunContext.RUN_CONTEXT;
import static io.restassured.RestAssured.given;

@Log4j2
public class BaseTest {

    TestConfig testConfig = new TestConfig();


    public void sendRequest(String url, String body, String variableName) {
        String URL = testConfig.getURL() + url;

        ValidatableResponse r = given().log().everything()
                .header("Content-Type", "application/json")
                .body(body)
                .post(URL)
                .then().log().ifError();

        RUN_CONTEXT.put(variableName, r);
    }
}
