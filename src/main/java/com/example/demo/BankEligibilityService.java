package com.example.demo;

import com.example.demo.model.EligiblityResponse;
import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class BankEligibilityService {
    @Autowired
    private RestTemplate bankRestTemplate;
    @Value( "${bank.eligibilty.url}" )
    private String url;
    public EligiblityResponse getEligibleCards(User user)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> entity =new HttpEntity<>(json, headers);
        ResponseEntity<EligiblityResponse> response = bankRestTemplate.postForEntity(url,entity, EligiblityResponse.class);
        return response.getBody();
    }

}
