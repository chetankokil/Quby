package eu.toon;

import eu.toon.services.AdviceService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("localhost")
public class AdviceRestController {

    private AdviceService adviceService;

    public AdviceRestController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }


    @PostMapping(value = "/rangeAdvice", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRangesWithAdvice(@RequestBody Map<String, Object> requestBody) {
        if(!requestBody.containsKey("range") || !requestBody.containsKey("advice"))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(adviceService.addRangeAdvice(requestBody));
    }


    @PutMapping(value = "/rangeAdvice", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateRangesWithAdvice(@RequestBody Map<String, Object> requestBody) {
        if(!requestBody.containsKey("range") || !requestBody.containsKey("advice"))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(adviceService.updateRangeAdvice(requestBody));
    }


    @GetMapping(value = "/rangeAdvices", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getRangeAdvices() {
        return ResponseEntity.ok(adviceService.getRangeAdvices());
    }


    @GetMapping(value = "/rangeAdvice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getRangeAdvices(@RequestParam(value = "range",required = false) String range, @RequestParam(value = "range",required = false) String advice) {
        return adviceService.getRangeAdvice(range, advice).map(rangeAdvice -> ResponseEntity.ok(rangeAdvice)).orElse(ResponseEntity.noContent().build());
    }



    @GetMapping(value = "/advice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAdviceForCity(@RequestParam(value = "city",defaultValue = "") String city) throws JSONException, IOException {
        JSONObject response = adviceService.getCityTemperature(city);
        return ResponseEntity.ok().body(response.toString());
    }





}
