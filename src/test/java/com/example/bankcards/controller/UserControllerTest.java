package com.example.bankcards.controller;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.dto.CardDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = BankCardsApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String body;

    @BeforeEach
    //@Sql({"/initialize/data.sql"})
    void setUp() throws JsonProcessingException {
        CardDto cardDto = new CardDto();
        cardDto.setUsername("Ibra");
        cardDto.setCardNumber("5469380043745632");
        cardDto.setCardNumberSender("5469380043745632");
        cardDto.setCardNumberRecipient("5469380043745798");
        cardDto.setBalance(500);
        body = objectMapper.writeValueAsString(cardDto);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllCards() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("http://localhost:8081/user/get_all_cards")
                .accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void render() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("http://localhost:8081/user/render")
                .accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void block() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("http://localhost:8081/user/block")
                .accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getBalance() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("http://localhost:8081/user/get_balance")
                .accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    private int requests(MockHttpServletRequestBuilder builder) throws Exception {
        AuthenticationRequestDto auth =  new AuthenticationRequestDto();
        auth.setUsername("Ibra");
        auth.setPassword("pass");

        String bodyAuth = objectMapper.writeValueAsString(auth);
        MvcResult token = mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyAuth)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        token.getResponse().getContentAsString();

        AuthenticationResponseDto responseDto = objectMapper.readValue(token.getResponse().getContentAsString(), AuthenticationResponseDto.class);

        builder.header("Authorization", "Bearer " + responseDto.getToken());
        MvcResult result = mockMvc.perform(builder).andReturn();

        return result.getResponse().getStatus();
    }
}