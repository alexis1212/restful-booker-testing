package com.aleksandrab.restfulBookerTest.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/reports/test-report", "json:target/reports/json/test-report.json"},
        features = {"classpath:features/"},
        glue = {"com.aleksandrab.restfulBookerTest.glue"}
)
public class RunnerTest {
}
