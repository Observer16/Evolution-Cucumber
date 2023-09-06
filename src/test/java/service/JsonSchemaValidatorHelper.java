package service;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonSchemaValidatorHelper {
    private static final Logger log = LoggerFactory.getLogger(JsonSchemaValidatorHelper.class);

    public static void validateResponseAgainstSchema(Response response, String nameSchema) {
        String schemaFilePath = "src/test/resources/schemas/" + nameSchema;
        File schemaFile = new File(schemaFilePath);

        if (!schemaFile.exists()) {
            log.warn("Файл схемы JSON '{}' не существует. Не выполняем валидацию.", schemaFilePath);
        }

        ValidatableResponse validatableResponse = response.then()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));

        if (!validatableResponse.extract().body().asString().isEmpty()) {
            log.info("Схема JSON соответствует ожидаемой схеме:");
        } else {
            log.error("Схема JSON не соответствует ожидаемой схеме.");
            log.error(validatableResponse.extract().body().asString());
        }
    }
}

