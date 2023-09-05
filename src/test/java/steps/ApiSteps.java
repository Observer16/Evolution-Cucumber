package steps;

import config.TestConfig;
import impl.BaseTest;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.И;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import service.HttpClient;
import service.RequestParam;
import service.RequestParamType;

import java.util.List;
import java.util.Map;

import static context.RunContext.RUN_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
public class ApiSteps extends BaseTest {

    TestConfig testConfig = new TestConfig();
    HttpClient httpClient = new HttpClient();

    @DataTableType
    public RequestParam requestParamEntry(Map<String, String> entry) {
        return new RequestParam(
                RequestParamType.valueOf(entry.get("type").toUpperCase()),
                entry.get("name"),
                entry.get("value"));
    }
    @И("^выполнен (GET|POST|PUT|DELETE) запрос на URL \"([^\"]*)\" с headers и parameters из таблицы. Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void sendHttpRequestSaveResponse(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String address = testConfig.getURL() + url;
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);
        System.out.println(response.asPrettyString());
    }

    @И("ответ содержит статус код {int}")
    public void response_status_code_is(int expectedStatusCode) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        assertEquals(expectedStatusCode, response.statusCode());
    }
}
