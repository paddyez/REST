package org.paddy.rest;

import org.apache.log4j.Logger;
import org.paddy.sfobjects.Account;
import org.paddy.utils.ConsoleColors;
import org.paddy.utils.NotifyingThread;
import org.paddy.utils.ResponseStatusCodes;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class PostAccount extends NotifyingThread {
    private static final Logger LOGGER = Logger.getLogger(PostAccount.class);
    private final String baseURI;

    public PostAccount(String baseURI) {
        this.baseURI = baseURI;
    }

    private void insertAccounts() {
        Set<String> accountsS = new LinkedHashSet<>();
        for (int i = 0; i < 1000; i++) {
            final Account a = new Account();
            a.setName("TestAccount Tester" + i);
            accountsS.add(a.getJSON());
        }
        String newAccounts = "{\"sObjects\":[";
        newAccounts += String.join(",", accountsS);
        newAccounts += "],\"info\":\"Useless information\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestBody = new HttpEntity<>(newAccounts, headers);
        ResponseEntity<String> result;
        try {
            result = restTemplate.postForEntity(this.baseURI, requestBody, String.class);
            HttpStatus statusHS = result.getStatusCode();
            int states = statusHS.value();
            String response = getResponse(result);
            if (statusHS == HttpStatus.OK) {
                LOGGER.info("Accounts created: server status code: " + states + "\n" + response);
            } else {
                LOGGER.error("Failed to create Accounts server status code: " + states + "\n" + response);
            }
        } catch (HttpServerErrorException httpServerErrorException) {
            int code = Integer.parseInt(Objects.requireNonNull(httpServerErrorException.getMessage()).substring(0, 3));
            LOGGER.error(ResponseStatusCodes.getPossibleCause("POST", code) + "\n", httpServerErrorException);
        }
    }

    private String getResponse(ResponseEntity<String> result) {
        String resultBody = result.getBody();
        HttpHeaders httpHeaders = result.getHeaders();
        Set<String> headersS = new LinkedHashSet<>();
        String resultHeadersS;
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headersS.add(entry.getKey() + ": " + entry.getValue());
        }
        resultHeadersS = String.join("\n", headersS);
        return "Headers:\n" + ConsoleColors.BLUE + resultHeadersS + ConsoleColors.WHITE + "\nBody:\n" + resultBody;
    }

    public void doRun() {
        insertAccounts();
    }
}
