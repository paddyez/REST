package org.paddy.rest;

import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Get {
    private static final Logger log = Logger.getLogger(Get.class);
    private static final String URI_BASE = "https://###SITENAME###-developer-edition.eu12.force.com/services/apexrest/###RestResource###";

    public Get() {
        String sfId = "0011r00001yq5UKAAY";
        String requestURI = URI_BASE + "?sObj=Account&q=all";//Id=" + sfId;
        String result;
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject(requestURI, String.class);
        log.info(result);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Id", sfId);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> response = restTemplate.exchange(requestURI, HttpMethod.GET, entity, String.class);
        result = response.getBody();
        log.info(result);
    }
}
