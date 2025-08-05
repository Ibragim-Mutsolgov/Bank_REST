package com.example.bankcards.controller;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest(classes = BankCardsApplication.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    private UserDetails userDetails;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @Sql({"/initialize/clean.sql", "/initialize/data.sql"})
    void authenticate() throws Exception {
        AuthenticationRequestDto auth =  new AuthenticationRequestDto();
        auth.setUsername("admin");
        auth.setPassword("admin");

        userDetails = userDetailsService.loadUserByUsername(auth.getUsername());

        String body = objectMapper.writeValueAsString(auth);
        MvcResult token = mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        token.getResponse().getContentAsString();

        AuthenticationResponseDto responseDto = objectMapper.readValue(token.getResponse().getContentAsString(), AuthenticationResponseDto.class);

        Assertions.assertEquals(true,
                jwtService.isTokenValid(responseDto.getToken(), userDetails));
    }

    @AfterAll
    @Sql({"/initialize/clean.sql"})
    public static void tearDown() {}
}
