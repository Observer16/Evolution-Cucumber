package steps;

import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import model.basket.BasketPatchBody;
import org.junit.Assert;

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
            String inBasket = row.get("quantity");
            RUN_CONTEXT.put("inBasket", inBasket);
            String productId = row.get("productId");
            String id = String.valueOf(row.get("productId"));
            RUN_CONTEXT.put("id", id);

            BasketPatchBody basketPatchBody = new BasketPatchBody(dataTable);
            String body = basketPatchBody.asJSON();

            // Добавляем логику для проверки body и кода ответа
            int responseCode = 400; // Пример кода ответа
            if (responseCode == 400) {
                RUN_CONTEXT.put("ErrorStepIndex", String.valueOf(currentStepIndex));
            }

            RUN_CONTEXT.put(variableName, body);
            currentStepIndex++;
        }
    }

    /**
     * Проверяем в ответе, что количество товара с ID в корзине увеличилось на указанное значение.
     *
     * @param  id         ID товара
     * @param  inBasket   Количество товара, на которое ожидается увеличение
     * @return            Нет возвращаемого значения
     */
    @Затем("проверяем в ответе, что количество товара с ID {string} в корзине увеличилось на {string}")
    public void проверяемВОтветеЧтоКоличествоТовараСIDВКорзинеУвеличилосьНа(String id, String inBasket) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        //System.out.println(response.asPrettyString());
        id = String.valueOf(RUN_CONTEXT.get("id", String.class));
        System.out.println(RUN_CONTEXT.get("inBasket", String.class));
        String responseBody = response.getBody().asString();

        // Распарсить JSON-ответ
        JsonPath jsonPath = new JsonPath(responseBody);

        // Извлечь значение inBasket из JSON для товара с указанным id
        String actualInBasket = jsonPath.getString("data.products.find { it.id == '" + id + "' }.inBasket");

        System.out.println(actualInBasket);

        // Сравнить actualInBasket с ожидаемым значением inBasket
        Assert.assertEquals(RUN_CONTEXT.get("inBasket"), actualInBasket);
    }
}