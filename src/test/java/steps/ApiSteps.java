package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.*;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.auth.ByPhonePost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import service.*;
import config.TestConfig;
import impl.BaseTest;
import static context.RunContext.RUN_CONTEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.JsonSchemaValidatorHelper;

public class ApiSteps extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);
    private final SchemaMapping schemaMapping = new SchemaMapping();
    TestConfig testConfig = new TestConfig();
    HttpClient httpClient = new HttpClient();
    private int currentStepIndex;

    /**
     * Преобразует заданную запись в объект RequestParam.
     *
     * @param entry карта, представляющая запись, которую нужно преобразовать
     * @return преобразованный объект RequestParam
     */
    @DataTableType
    public RequestParam requestParamEntry(Map<String, String> entry) {
        return new RequestParam(
                RequestParamType.valueOf(entry.get("type").toUpperCase()),
                entry.get("name"),
                entry.get("value"));
    }

    /**
     * Отправляет HTTP-запрос с указанным методом, URL, заголовками и параметрами из таблицы.
     * Полученный ответ сохраняется в указанной переменной.
     *
     * @param method       метод HTTP (GET, POST, PUT или DELETE)
     * @param url          URL для отправки запроса
     * @param variableName имя переменной, в которую нужно сохранить ответ
     * @param paramsTable  таблица с заголовками и параметрами для запроса
     */
    @Когда("^выполнен (GET|POST|PUT|DELETE|PATCH) запрос на URL \"([^\"]*)\" с headers и parameters из таблицы. Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void sendHttpRequestSaveResponse(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String productId = RUN_CONTEXT.get("productId", String.class);
        String address = testConfig.getURL() + url;
        if (productId != null && address.contains("{productId}")) {
            address = address.replace("{productId}", productId);
        }
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);
        System.out.println(response.asPrettyString());

        RUN_CONTEXT.deleteKey("productId");
    }

    /**
     * Проверяет, что код статуса ответа равен ожидаемому коду.
     *
     * @param expectedStatusCode ожидаемый код статуса для проверки
     */
    @И("ответ содержит статус код {int}")
    public void response_status_code_is(int expectedStatusCode) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        assertEquals(expectedStatusCode, response.statusCode());
    }

    /**
     * Проверяет, что ответ соответствует ожидаемой схеме.
     *
     * @param nameSchema название файла схемы
     */
    @Тогда("проверяем, что схема {string} соответствует схеме из ответа")
    public void CheckingResponseMatchesExpectedPattern(String nameSchema) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        JsonSchemaValidatorHelper.validateResponseAgainstSchema(response, nameSchema);
    }

    /**
     * Устанавливает идентификатор продукта.
     *
     * @param arg продукт с идентификатором
     */
    @Дано("продукт с идентификатором")
    public void ProductId(List<String> arg) {
        String productId = arg.get(0);
        RUN_CONTEXT.put("productId", productId);
    }

    @Затем("проверяем, что в заголовке {string} пришел новый токен")
    public void checkIfTokenIsNew(String response) {
        Response parsedResponse = RUN_CONTEXT.get("response", Response.class);
        String authToken = TokenManager.readTokenFromFile();
        String newAuthToken = parsedResponse.getHeader("X-Auth-Token");
        Assertions.assertNotEquals(authToken, newAuthToken);
    }

    @И("^выполняем (GET|POST|PUT|DELETE|PATCH) запрос на URL \"([^\"]*)\" для получения данных из профиля пользователя. Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void checkAuthPassedWhenLogInUserProfile(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String address = testConfig.getURL() + url;
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);
        System.out.printf("%s%n", response.asPrettyString());
    }

    @Когда("^выполнен (GET|POST|PUT|DELETE|PATCH) запрос на URL \"([^\"]*)\" полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void выполненPOSTЗапросНаURL(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String address = testConfig.getURL() + url;
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);
        System.out.printf("%s%n", response.asPrettyString());
    }

    /**
     * Извлекает ID профиля из ответа и проверяет, соответствует ли его формат ожидаемой маске.
     *
     * @param ID ожидаемая маска ID профиля
     * @return void
     */
    @Затем("получаем из ответа {string} ID профиля и проверяем, что маска ID правильная")
    public void getProfileIdResponse(String ID) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        String profileId = response.path("data.profile.id").toString().trim(); // Преобразование в строку и обрезка лишних символов

        // Проверка соответствия маске ID с использованием регулярного выражения
        boolean isIdMaskMatch = Pattern.matches("^[0-9a-zA-Z-]+$", profileId);
        System.out.println(isIdMaskMatch);
        System.out.println("profileId: [" + profileId + "]");

        // Проверка пройдена, если маска ID соответствует
        if (!isIdMaskMatch) {
            throw new AssertionError("Маска ID не соответствует ожидаемому формату");
        }
    }

    @Затем("получаем из ответа {string} ID профиля и проверяем, что он сформирован в соответствии с маской ID")
    public void getRegProfileIdResponse(String ID) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        String profileId = response.path("data.id").toString().trim(); // Преобразование в строку и обрезка лишних символов

        // Проверка соответствия маске ID с использованием регулярного выражения
        boolean isIdMaskMatch = Pattern.matches("^[0-9a-zA-Z-]+$", profileId);
        System.out.println(isIdMaskMatch);
        System.out.println("profileId: [" + profileId + "]");

        // Проверка пройдена, если маска ID соответствует
        if (!isIdMaskMatch) {
            throw new AssertionError("Маска ID не соответствует ожидаемому формату");
        }
    }

    @Затем("проверяем, что в теле ответа {string} есть сообщение {string}")
    public void checkResponseBodyContainsMessage(String responseVariable, String message) {
        Response response = RUN_CONTEXT.get(responseVariable, Response.class);
        String responseBody = response.getBody().asString();

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray errorsArray = jsonResponse.getJSONArray("errors");

        boolean messageFound = false;
        for (int i = 0; i < errorsArray.length(); i++) {
            JSONObject errorObject = errorsArray.getJSONObject(i);
            String errorMessage = errorObject.getString("message");
            if (errorMessage.equals(message)) {
                messageFound = true;
                break;
            }
        }

        Assertions.assertTrue(messageFound, "Ожидаемое сообщение \"" + message + "\" в теле ответа не найдено");
    }

    @И("проверяем, что тело ответа соответствует данным из таблицы")
    public void checkResponseBodyContains(List<RequestParam> paramsTable) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        String responseBody = response.getBody().asString();

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONObject dataObject = jsonResponse.getJSONObject("data");

        for (RequestParam param : paramsTable) {
            String paramName = param.getName();
            String paramValue = param.getValue();
            String actualValue = dataObject.optString(paramName);

            assertEquals(paramValue, actualValue, "Ожидаемое значение параметра \"" + paramName + "\" не совпадает");
        }
    }

    @Затем("создан рандомный номер телефона для сохранения в переменную {string}")
    public void createRandomNumBody(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        String phone = RandomDataGenerator.getRandomDateFor(RandomDataTypeNames.NUMBER);
        ByPhonePost byPhonePost = new ByPhonePost("79" + phone);
        String body = byPhonePost.asJSON();
        RUN_CONTEXT.put(variableName, body);
        }
}