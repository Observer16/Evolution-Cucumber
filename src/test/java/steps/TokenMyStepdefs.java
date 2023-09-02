package steps;

import config.TestConfig;
import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import model.TokenBody;
import org.junit.Assert;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static context.RunContext.RUN_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenMyStepdefs extends BaseTest {

    public static String body;

    @Когда("созданo тело с параметрами")
    public void createBody(DataTable dataTable) {
        TokenBody tokenBody = new TokenBody(dataTable);
        body = tokenBody.asJSON();
    }

    @Затем("отправлен запрос с телом на {string} и ответ записан в переменную {string}")
    public void createRequestWithBody(String url, String variableName, DataTable dataTable) {
        TokenBody tokenBody = new TokenBody(dataTable);
        String body = tokenBody.asJSON();
        sendRequestWithBody(url, body, variableName, Method.POST);
    }

    @И("Response status code is: {string}")
    public void response_status_code_is(String expectedStatusCode) {
        ValidatableResponse response = RUN_CONTEXT.get("response", ValidatableResponse.class);
        int actualStatusCode = response.extract().statusCode();
        int expectedStatusCodeInt = Integer.parseInt(expectedStatusCode);
        assertEquals(expectedStatusCodeInt, actualStatusCode);
    }

    @Тогда("получаем гостевой токен из заголовка ответа {string} и записываем его в переменную {string}")
    public void getGuestToken(String responseVariable, String tokenVariable) {
        ValidatableResponse response = RUN_CONTEXT.get(responseVariable, ValidatableResponse.class);
        String authToken = response.extract().header("X-Auth-Token");

        saveTokenToFile(authToken);
    }
    private void saveTokenToFile(String token) {
        try {
            FileWriter fileWriter = new FileWriter(TestConfig.AUTH_TOKEN);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(token);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
