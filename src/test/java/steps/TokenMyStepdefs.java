package steps;

import config.TestConfig;
import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import model.TokenBody;
import service.HttpClient;
import service.RequestParam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static context.RunContext.RUN_CONTEXT;


@Log4j2
public class TokenMyStepdefs extends BaseTest {

    TestConfig testConfig = new TestConfig();
    HttpClient httpClient = new HttpClient();
    private int currentStepIndex = 0;


    /**
     * Создает объект тела токена и сохраняет его в переменную.
     *
     * @param  variableName    имя переменной, в которую нужно сохранить объект тела токена
     * @param  dataTable       таблица данных, содержащая информацию о сборке, версии и платформе
     */
    @Дано("создан объект и сохранен в переменную {string}")
    public void createTokenBody(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String build = row.get("build");
            String version = row.get("version");
            String platform = row.get("platform");

            TokenBody tokenBody = new TokenBody(build, version, platform);
            String body = tokenBody.asJSON();

            // Добавляем логику для проверки body и кода ответа
            int responseCode = 400; // Пример кода ответа
            if (responseCode == 400) {
                RUN_CONTEXT.put("ErrorStepIndex", String.valueOf(currentStepIndex));
                System.out.println(currentStepIndex);
            }

            RUN_CONTEXT.put(variableName, body);
            System.out.println(RUN_CONTEXT.get(variableName, String.class));

            currentStepIndex++;
        }
    }

    @Затем("^выполнен (GET|POST|PUT|DELETE|PATCH) запрос на URL \"([^\"]*)\" и ответ записан в переменную \"([^\"]*)\"$")
    public void sendRequestWithBody(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String productId = RUN_CONTEXT.get("productId", String.class);
        String address = testConfig.getURL() + url;
        if (productId != null && address.contains("{productId}")) {
            address = address.replace("{productId}", productId);
        }
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequestWithoutToken(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);

        //String newAuthToken = response.getHeader("X-Auth-Token");
    }


    /**
     * Получает гостевой токен из заголовка ответа и записывает его в переменную.
     *
     * @param  responseVariable  строка, содержащая имя переменной с объектом Response
     * @param  tokenVariable     строка, содержащая имя переменной, в которую будет записан гостевой токен
     */
    @Тогда("получаем токен из заголовка ответа {string} и записываем его в переменную {string}")
    public void getGuestToken(String responseVariable, String tokenVariable) {
        Response response = RUN_CONTEXT.get(responseVariable, Response.class);
        String authToken = response.getHeader("X-Auth-Token");
        saveTokenToFile(authToken);
        System.out.println(authToken);
        RUN_CONTEXT.put(tokenVariable, authToken);
    }

    /**
     * Сохраняет указанный токен в файл.
     *
     * @param  token токен для сохранения
     */
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

