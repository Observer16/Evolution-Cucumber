package steps.basket;

import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import lombok.extern.log4j.Log4j2;
import model.basket.BasketPut;
import java.util.List;
import java.util.Map;
import static context.RunContext.RUN_CONTEXT;

@Log4j2
public class BasketPutMyStepdefs extends BaseTest {

    private int currentStepIndex = 0;
    @Затем("создан объект таблицы для сохранения в переменную {string} при отчистки корзины")
    public void createTableBody(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String operation = row.get("operation");

            BasketPut basketPut = new BasketPut(operation);
            String body = basketPut.asJSON();

            // Добавляем логику для проверки body и кода ответа
            int responseCode = 400; // Пример кода ответа
            if (responseCode == 400) {
                RUN_CONTEXT.put("ErrorStepIndex", String.valueOf(currentStepIndex));
            }

            RUN_CONTEXT.put(variableName, body);
            currentStepIndex++;
        }
    }
}
