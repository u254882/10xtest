package com.example.demo.controller;

import com.example.demo.BankEligibilityService;
import com.example.demo.model.EligiblityResponse;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EligibilityController {
    @Autowired
    private BankEligibilityService bankEligibilityService;

    @PostMapping("/eligibility/check")
    public List<String> eligibleCardsForUser(@Valid @RequestBody User user) {
        EligiblityResponse eligibleCards = bankEligibilityService.getEligibleCards(user);
        List<String> cards = eligibleCards.getCards();
        return cards;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
