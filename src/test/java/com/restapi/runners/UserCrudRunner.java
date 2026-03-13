package com.restapi.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/user",
        glue = {"com.restapi.stepdefinitions"},
        plugin = {
            "pretty", 
            "json:target/cucumber-reports/cucumber.json",
            "rerun:target/rerun.txt"
        },
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class UserCrudRunner {
}
