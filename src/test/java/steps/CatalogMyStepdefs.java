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

public class CatalogMyStepdefs extends BaseTest {

    @Затем("^выполнен (GET|DELETE) запрос на URL \"([^\"]*)\". Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void getCategoriesRequest(String method, String url, String variableName) {
        sendRequest(url, variableName, Method.valueOf(method));
    }
}