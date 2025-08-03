package com.example.bankcards.controller;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.After;
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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String bodyRequest;

    private String bodyAdmin;

    private String bodyCard;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setUsername("Ibragim");
        request.setPassword("1234");
        request.setFirstName("Ibragim");
        request.setLastName("Ibragim");

        AdminRequestDto adminDto =  new AdminRequestDto();
        adminDto.setUsername("Ibra");

        CardDto cardDto =  new CardDto();
        cardDto.setCardNumber("5469380043745632");

        bodyCard = objectMapper.writeValueAsString(cardDto);
        bodyRequest = objectMapper.writeValueAsString(request);
        bodyAdmin = objectMapper.writeValueAsString(adminDto);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void register() throws Exception {
        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders
                        .post("http://localhost:8081/admin/register")
                        .content(bodyRequest).contentType(MediaType.APPLICATION_JSON);

        int result = requests(mockRequest);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllUsers() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("http://localhost:8081/admin/get_all_users")
                .accept(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteAllUsers() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("http://localhost:8081/admin/delete_all_users")
                .accept(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("http://localhost:8081/admin/get_user")
                .accept(MediaType.APPLICATION_JSON).content(bodyAdmin).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("http://localhost:8081/admin/delete_user")
                .accept(MediaType.APPLICATION_JSON).content(bodyAdmin).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void createCard() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("http://localhost:8081/admin/create_card")
                .accept(MediaType.APPLICATION_JSON).content(bodyAdmin).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void blockCard() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("http://localhost:8081/admin/block_card")
                .accept(MediaType.APPLICATION_JSON).content(bodyCard).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void unblockCard() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("http://localhost:8081/admin/unblock_card")
                .accept(MediaType.APPLICATION_JSON).content(bodyCard).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteCard() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("http://localhost:8081/admin/delete_card")
                .accept(MediaType.APPLICATION_JSON).content(bodyCard).contentType(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllCards() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("http://localhost:8081/admin/get_all_cards")
                .accept(MediaType.APPLICATION_JSON);
        int result = requests(builder);

        Assertions.assertEquals(200, result);
    }

    private int requests(MockHttpServletRequestBuilder builder) throws Exception {
        AuthenticationRequestDto auth =  new AuthenticationRequestDto();
        auth.setUsername("admin");
        auth.setPassword("admin");

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