import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@ExtendWith(AllureJunit5.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        publish = true
        //tags = "@startTestCase"
)
public class RunCucumberTest {
}

