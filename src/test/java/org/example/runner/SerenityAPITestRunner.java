

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

    @RunWith(CucumberWithSerenity.class)
    @CucumberOptions(plugin = { "pretty" }, features = "src/test/resources/features/create_case.feature", glue = {
            "org.example.definitions" })

    public class SerenityAPITestRunner {

    }


