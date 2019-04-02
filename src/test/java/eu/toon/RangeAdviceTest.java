package eu.toon;

import eu.toon.dao.AdviceRangeDAO;
import eu.toon.models.Range;
import eu.toon.models.RangeAdvice;
import eu.toon.services.AdviceService;
import eu.toon.services.impl.AdviceServiceImpl;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class RangeAdviceTest {


    @Mock
    TestRestTemplate restTemplate;

    @Mock
    private AdviceRangeDAO adviceRangeDAO;

    @InjectMocks
    private AdviceService adviceService = new AdviceServiceImpl(adviceRangeDAO, null, null);


    @Test
    public void should_failWhenRangeAdviceIsNull() {
        Map<String, Object> rangeAdviceMap = new HashMap<>();

        Throwable throwable = catchThrowable(() -> adviceService.addRangeAdvice(rangeAdviceMap));

        BDDAssertions.then(throwable).as("an IllegalArgumentException is thrown when body is null")
                .isInstanceOf(IllegalArgumentException.class)
                .as("Check if the request body is present");
    }


    @Test
    public void should_failWhenAdviceIsEmpty() {
        Map<String, Object> rangeAdviceMap = new HashMap<>();
        rangeAdviceMap.put("advice", "");
        rangeAdviceMap.put("range","-10,0");

        Throwable throwable = catchThrowable(() -> adviceService.addRangeAdvice(rangeAdviceMap));

        BDDAssertions.then(throwable).as("an IllegalArgumentException is thrown when Advice or range is null")
                .isInstanceOf(IllegalArgumentException.class)
                .as("Check if the Advice and Range is Specified");
    }


    @Test
    public void should_failWhenRangeIsNull() {
        Map<String, Object> rangeAdviceMap = new HashMap<>();
        rangeAdviceMap.put("advice", "");


        Throwable throwable = catchThrowable(() -> adviceService.addRangeAdvice(rangeAdviceMap));

        BDDAssertions.then(throwable).as("an IllegalArgumentException is thrown when range is null")
                .isInstanceOf(IllegalArgumentException.class)
                .as("Check if the Range is Specified");
    }


    @Test
    public void should_addRangeAdvice()  {
        Map<String, Object> rangeAdviceMap = new HashMap<>();
        rangeAdviceMap.put("advice", "Russian Coat");
        rangeAdviceMap.put("range","-10,0");


        given(adviceRangeDAO.addRangeAdvice(any(RangeAdvice.class))).willReturn(new RangeAdvice("Russian Coat",new Range(-10,0)));

        RangeAdvice addRangeAdvice = adviceService.addRangeAdvice(rangeAdviceMap);

        assertThat(addRangeAdvice).isNotNull();
        assertThat(addRangeAdvice.getAdvice()).isNotEmpty();
        assertThat(addRangeAdvice.getRange()).isNotNull();
        assertThat(addRangeAdvice.getAdvice()).isEqualTo("Russian Coat");

    }


    @Test
    public void should_returnAllRangeAdvicesPair() {
        given(adviceRangeDAO.getRangeAdvices()).willReturn(Stream.of(new RangeAdvice("Russian Coat", new Range(-10,0)),
                new RangeAdvice("Summer Suites", new Range(31, 40))).collect(Collectors.toList()));

        List<RangeAdvice> returnList = adviceService.getRangeAdvices();

        assertThat(returnList).isNotNull();
        assertThat(returnList.size()).isEqualTo(2);
    }


    @Test
    public void should_updateAdviceIfRangeExists() {
        Range range = new Range(-10,0);
        Map<String, Object> rangeAdviceMap = new HashMap<>();
        rangeAdviceMap.put("advice", "Russian American Coat");
        rangeAdviceMap.put("range","-10,0");
        given(adviceRangeDAO.getRangeAdvices()).willReturn(Stream.of(new RangeAdvice("Russian Coat", new Range(-10,0)),
                new RangeAdvice("Summer Suites", new Range(31, 40))).collect(Collectors.toList()));

        given(adviceRangeDAO.updateRangeAdvice(any(RangeAdvice.class))).willReturn(new RangeAdvice("Russian American Coat",new Range(-10,0)));

        RangeAdvice rangeAdvice = adviceService.updateRangeAdvice(rangeAdviceMap);
        assertThat(rangeAdvice).isNotNull();
        assertThat(rangeAdvice.getAdvice()).isNotNull();
        assertThat(rangeAdvice.getAdvice()).isEqualTo("Russian American Coat");
    }

}
