package steps;

import config.TestConfig;
import context.RunContext;
import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;
import model.TokenBody;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static context.RunContext.RUN_CONTEXT;


@Log4j2
public class TokenMyStepdefs extends BaseTest {


    private int currentStepIndex = 0;

    @Затем("создан объект и сохранен в переменную {string}")
    public void createTokenBody(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String build = row.get("build");
            String version = row.get("version");
            String platform = row.get("platform");

            TokenBody tokenBody = new TokenBody(build, version, platform);
            String body = tokenBody.asJSON();

            // Добавьте здесь логику для проверки body и кода ответа
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


    @Тогда("получаем гостевой токен из заголовка ответа {string} и записываем его в переменную {string}")
    public void getGuestToken(String responseVariable, String tokenVariable) {
        Response response = RUN_CONTEXT.get(responseVariable, Response.class);
        String authToken = response.getHeader("X-Auth-Token");
        saveTokenToFile(authToken);
        System.out.println(authToken);
        RUN_CONTEXT.put(tokenVariable, authToken);
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
