package eu.toon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("abc")
@Component
public class AppConfig {

    private String testval;

    public String getTestval() {
        return testval;
    }

    public void setTestval(String testval) {
        this.testval = testval;
    }
}
