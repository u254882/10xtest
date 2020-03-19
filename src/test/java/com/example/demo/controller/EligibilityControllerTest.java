package com.example.demo.controller;

import com.example.demo.BankEligibilityService;
import com.example.demo.model.EligiblityResponse;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EligibilityController.class)
class EligibilityControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BankEligibilityService bankEligibilityService;

    @Test
    public void shouldReturn200() throws Exception {
        EligiblityResponse expected = EligiblityResponse.builder().cards(List.of("C1")).build();
        when(bankEligibilityService.getEligibleCards(any(User.class))).thenReturn(expected);
        mockMvc.perform(post("/eligibility/check")
                .content(asJsonString(getTestUser()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expected.getCards())));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(bankEligibilityService, times(1)).getEligibleCards(userCaptor.capture());
        assertEquals(userCaptor.getValue().getName(), "Boris");
        assertEquals(userCaptor.getValue().getAddress(), "Downing 10");
        assertEquals(userCaptor.getValue().getEmail(), "boris@asd.com");
    }

    @Test
    public void shouldReturn400() throws Exception {
        mockMvc.perform(post("/eligibility/check")
                .content(asJsonString(getTestUserMissingFields()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    private User getTestUserMissingFields() {
        return new User("", "", "");
    }

    private User getTestUser() {
        return new User("Boris", "Downing 10", "boris@asd.com");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}