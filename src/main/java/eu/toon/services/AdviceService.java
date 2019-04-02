package eu.toon.services;

import eu.toon.models.RangeAdvice;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdviceService {

    RangeAdvice addRangeAdvice(Map<String, Object> rangeAdvice);

    List<RangeAdvice> getRangeAdvices();

    RangeAdvice updateRangeAdvice(Map<String, Object> rangeAdvice);

    double getTemperature(String city) throws IOException, JSONException;

    JSONObject getCityTemperature(String city) throws IOException, JSONException;

    Optional<RangeAdvice> getRangeAdvice(String range, String advice);
}
