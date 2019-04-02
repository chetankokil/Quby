package eu.toon;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static java.util.Objects.isNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class AdviceBaseClass {

    private static final String CURRENT_TEMPERATURE = "currentTemperature";
    private static final String ADVICE = "advice";

    private static final String RUSSIAN_COAT = "Russian coat";
    private static final String US_PARKA = "US Parka";
    private static final String REGULAR_JACKET = "Regular Jacket";
    private static final String T_SHIRT = "T-shirt and flip-flops";
    private static final String SWIMMING = "Swimming costume";

    private static final Condition<Map<String, Object>> RIGHT_ADVICE = new Condition<Map<String, Object>>() {

        @Override
        public boolean matches(final Map<String, Object> map) {
            if (!isNull(map.get(CURRENT_TEMPERATURE)) && !isNull(map.get(ADVICE))) {
                final String advice = (String) map.get(ADVICE);
                final Integer currentTemperature = (Integer) map.get(CURRENT_TEMPERATURE);

                return
                        (currentTemperature >= -10 && currentTemperature <= 0 && RUSSIAN_COAT.equals(advice))
                                ||
                                (currentTemperature >= 1 && currentTemperature <= 10 && US_PARKA.equals(advice))
                                ||
                                (currentTemperature >= 11 && currentTemperature <= 20 && REGULAR_JACKET.equals(advice))
                                ||
                                (currentTemperature >= 21 && currentTemperature <= 30 && T_SHIRT.equals(advice))
                                ||
                                (currentTemperature >= 31 && currentTemperature <= 40 && SWIMMING.equals(advice));
            }
            return false;
        }

    };


    @Autowired
    private AdviceRestController controller;

    @Before
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(controller);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }




    public static void assertValidClothingAdvice(final Map<String, Object> response) {
        assertThat(response)
                .containsKeys(CURRENT_TEMPERATURE, ADVICE)
                .has(RIGHT_ADVICE);
    }

}
