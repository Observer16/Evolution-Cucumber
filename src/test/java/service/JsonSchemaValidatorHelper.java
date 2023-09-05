package service;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.ApiSteps;

import java.io.File;


public class JsonSchemaValidatorHelper {
    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);

    public static void validateResponseAgainstSchema(Response response, String schemaFilePath) {
        File schemaFile = new File(schemaFilePath);

        if (!schemaFile.exists()) {
            log.warn("Файл схемы JSON '{}' не существует. Не выполняем валидацию.", schemaFilePath);
            return;
        }

        ValidatableResponse validatableResponse = response.then()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));

        if (!validatableResponse.extract().body().asString().isEmpty()) {
            log.error("Схема JSON не соответствует ожидаемой схеме:");
            log.error(validatableResponse.extract().body().asString());
        } else {
            log.info("Схема JSON соответствует ожидаемой схеме.");
        }
    }
}
