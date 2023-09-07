package steps;

import config.TestConfig;
import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import model.BasketPatchBody;
import model.TokenBody;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static context.RunContext.RUN_CONTEXT;


@Log4j2
public class BasketPatchMyStepdefs extends BaseTest {

    private int currentStepIndex = 0;
    @Затем("создан объект для сохранения в переменную {string}")
    public void createBasketPatchBody(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String quantity = row.get("quantity");
            String productId = row.get("productId");

            BasketPatchBody basketPatchBody = new BasketPatchBody(dataTable);
            String body = basketPatchBody.asJSON();

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
}