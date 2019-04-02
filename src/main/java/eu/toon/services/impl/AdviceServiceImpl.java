package eu.toon.services.impl;

import eu.toon.config.AppConfig;
import eu.toon.dao.AdviceRangeDAO;
import eu.toon.models.Range;
import eu.toon.models.RangeAdvice;
import eu.toon.services.AdviceService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdviceServiceImpl implements AdviceService {

    private AdviceRangeDAO adviceRangeDAO;

//    @Autowired
    AppConfig appConfig;


//    @Autowired
    RestTemplate restTemplate;

    public AdviceServiceImpl(AdviceRangeDAO adviceRangeDAO, RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
        this.adviceRangeDAO = adviceRangeDAO;
    }

    @Override
    public RangeAdvice addRangeAdvice(Map<String, Object> rangeAdviceMap) {
        RangeAdvice rangeAdvice = validateAndCreateRangeAdvice(rangeAdviceMap);
        return adviceRangeDAO.addRangeAdvice(rangeAdvice);
    }

    private RangeAdvice validateAndCreateRangeAdvice(Map<String, Object> rangeAdviceMap) {
        String range = (String)rangeAdviceMap.getOrDefault("range","0,0");
        String advice = (String)rangeAdviceMap.getOrDefault("advice","");
        String[] rangeSplit = range.split(",");
        double min =  Double.parseDouble(rangeSplit[0]);
        double max =  Double.parseDouble(rangeSplit[1]);
        RangeAdvice rangeAdvice = new RangeAdvice();
        rangeAdvice.setRange(new Range(min,max));
        rangeAdvice.setAdvice(advice);

        validateRangeAdvice(rangeAdvice);
        return rangeAdvice;
    }

    private void validateRangeAdvice(RangeAdvice rangeAdvice) {
        if (rangeAdvice == null)
            throw new IllegalArgumentException("Invalid Input");

        if (StringUtils.isEmpty(rangeAdvice.getAdvice()))
            throw new IllegalArgumentException("Advice cannot be null or Empty");

        if (rangeAdvice.getRange() == null)
            throw new IllegalArgumentException("Range Cannot be null");
    }

    @Override
    public List<RangeAdvice> getRangeAdvices() {
        return adviceRangeDAO.getRangeAdvices();
    }

    @Override
    public RangeAdvice updateRangeAdvice(Map<String, Object> rangeAdviceMap) {
        RangeAdvice rangeAdvice = validateAndCreateRangeAdvice(rangeAdviceMap);
        Optional<RangeAdvice> optionalRangeAdvice = getRangeAdvices().stream().filter(ra -> ra.getRange().equals(rangeAdvice.getRange())).findFirst();
        return optionalRangeAdvice.map(ra -> adviceRangeDAO.updateRangeAdvice(rangeAdvice)).orElse(new RangeAdvice(null, null));
    }





    @Override
    public double getTemperature(String city) throws JSONException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
        String apiKey = appConfig.getTestval();
        String endUrl = String.format(url, city, apiKey);

        ResponseEntity<String> response = restTemplate.exchange(endUrl, HttpMethod.GET, null, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            if(jsonObject.has("main")) {
                return jsonObject.getJSONObject("main").has("temp")
                            ? jsonObject.getJSONObject("main").getDouble("temp")
                            : -100.0;
            }

        }
        return -100.0;
    }

    @Override
    public JSONObject getCityTemperature(String city) throws IOException, JSONException {
        double cityTem = getTemperature(city);
        if(cityTem == -100.0)
            throw new IOException("Error getting Temperature for city ="+city);
        int currTemp = new Double(cityTem).intValue();
        String advice = adviceRangeDAO.getAdvice(currTemp);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("city", city);
        jsonObject.put("currentTemperature",currTemp);
        jsonObject.put("advice",advice);

        return jsonObject;
    }

    @Override
    public Optional<RangeAdvice> getRangeAdvice(String range, String advice) {
        return adviceRangeDAO.getRangeAdvice(range, advice);
    }


}
