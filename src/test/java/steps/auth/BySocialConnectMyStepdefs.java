package steps.auth;

import impl.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import lombok.extern.log4j.Log4j2;
import model.BySocialConnect;
import java.util.List;
import java.util.Map;

import static context.RunContext.RUN_CONTEXT;

@Log4j2
public class BySocialConnectMyStepdefs extends BaseTest {

    @Затем("создан объект BySocialConnect и сохранен в переменную {string}")
    public void createBySocialConnect(String variableName, DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String provider = row.getOrDefault("provider", "");
            String code = row.getOrDefault("code", "");

            BySocialConnect bySocialConnect = new BySocialConnect(provider, code);
            String body = bySocialConnect.asJSON();

            RUN_CONTEXT.put(variableName, body);
            System.out.println(RUN_CONTEXT.get(variableName, String.class));
        }
    }
}