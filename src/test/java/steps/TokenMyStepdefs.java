package steps;

import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.ValidatableResponse;
import model.TokenBody;
import org.junit.Assert;

import static context.RunContext.RUN_CONTEXT;

public class TokenMyStepdefs extends BaseTest {

    public static String body;

    @Когда("созданo тело с параметрами")
    public void createBody(DataTable dataTable) {
        TokenBody tokenBody = new TokenBody(dataTable);
        body = tokenBody.asJSON();
    }

    @Затем("отправлен запрос на {string} и ответ записан в переменную {string}")
    public void createRequestWithBody(String url, String variableName, DataTable dataTable) {
        TokenBody tokenBody = new TokenBody(dataTable);
        String body = tokenBody.asJSON();
        sendRequest(url, body, variableName);
    }

    @И("Response status code is: {string}")
    public void verifyResponseStatus(String expectedStatus) {
        ValidatableResponse response = RUN_CONTEXT.get("response", ValidatableResponse.class);
        int actualStatus = response.extract().statusCode();
        int parsedExpectedStatus = Integer.parseInt(expectedStatus);
        Assert.assertEquals(actualStatus, parsedExpectedStatus);
    }

    @Тогда("получаем гостевой токен из заголовка ответа {string} и записываем его в переменную {string}")
    public void getGuestToken(String variableName, String token) {
        ValidatableResponse response = RUN_CONTEXT.get(variableName, ValidatableResponse.class);
        RUN_CONTEXT.put(token, response.extract().header("X-Auth-Token"));
        System.out.println(response.extract().header("X-Auth-Token"));
    }
}
