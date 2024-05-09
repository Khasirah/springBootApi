package com.peppo.springbootapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.LoginUserRequest;
import com.peppo.springbootapi.model.TokenResponse;
import com.peppo.springbootapi.model.WebResponse;
import com.peppo.springbootapi.repository.UserRepository;
import com.peppo.springbootapi.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testLoginFailedUserNotFound() throws Exception {

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("test");
        loginUserRequest.setPassword("rahasia");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testLoginFailedWrongPassword() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        userRepository.save(user);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("test");
        loginUserRequest.setPassword("salah");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testLoginSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        userRepository.save(user);

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setUsername("test");
        loginUserRequest.setPassword("rahasia");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TokenResponse> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            assertNull(response.getErrors());
            assertNotNull(response.getData().getToken());
            assertNotNull(response.getData().getExpiredAt());

            User userDb = userRepository.findById("test").orElse(null);
            assertNotNull(userDb);

            assertEquals(userDb.getToken(), response.getData().getToken());
            assertEquals(userDb.getTokenExpiredAt(), response.getData().getExpiredAt());
        });
    }

    @Test
    void testLogoutFailed() throws Exception {
        mockMvc.perform(
                delete("/api/auth/logout")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testLogoutSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000000L);
        userRepository.save(user);

        mockMvc.perform(
                delete("/api/auth/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            assertNull(response.getErrors());
            assertEquals("Berhasil keluar", response.getData());

            User userDb = userRepository.findById("test").orElse(null);
            assertNotNull(userDb);
            assertNull(userDb.getToken());
            assertNull(userDb.getTokenExpiredAt());
        });
    }
}