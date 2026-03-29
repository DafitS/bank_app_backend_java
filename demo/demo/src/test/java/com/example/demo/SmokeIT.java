package com.example.demo;

import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.option.RoleType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.AbstractContainerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
public class SmokeIT extends AbstractContainerIT {

    @Autowired
    private UserRepository userRepository;



    @Autowired

    private MockMvc mockMvc;

    @Test
    public void contextLoads() {

        List<User> users = userRepository.findAll();

        users.forEach(System.out::println);
        userRepository.save(new User(null, "Dawid", "Test", "test@localhost.com", "Zaq12wsx", "11111112222", RoleType.STANDARD_USER, new ArrayList<>()));
        List<User> usersFinds = userRepository.findAll();

        usersFinds.forEach(System.out::println);
    }

    @Test
    public void healthEndpointReturnsOk() throws Exception {

    }

}
 