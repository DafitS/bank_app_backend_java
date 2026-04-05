package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.option.RoleType;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@DataJpaTest
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeIT {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

//        List<User> users = userRepository.findAll();
//
//        users.forEach(System.out::println);
        userRepository.save(new User(null, "Dawid", "Test", "test@localhost.com", "Zaq12wsx", "11111112222", RoleType.STANDARD_USER, new ArrayList<>(), new ArrayList<>()));
//        List<User> usersFinds = userRepository.findAll();
//
//        usersFinds.forEach(System.out::println);
    }

    @Test
    public void getUserById() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))) //zmienic to jest przykład
                        .andExpect(status().isCreated()) //sprawdzic i ustawic dobry status jaki zwracam'
                        .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        UserResponse created = objectMapper.readValue(json, UserResponse.class); //Pod moja metode deserializacja

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("test@localhost.com"));

    }

}
 