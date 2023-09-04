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
import static context.RunContext.RUN_CONTEXT;


@Log4j2
public class TokenMyStepdefs extends BaseTest {


    @Затем("создан объект и сохранен в переменную {string}")
    public void createTokenBody(String variableName, DataTable dataTable) {
        TokenBody tokenBody = new TokenBody(dataTable);
        String body = tokenBody.asJSON();
        RUN_CONTEXT.put(variableName, body); // записываем body в переменную
        System.out.println(RUN_CONTEXT.get("body", String.class));
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
