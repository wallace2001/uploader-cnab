package com.wallace.uploadcnab.fixture;

import com.wallace.uploadcnab.domain.User;
import com.wallace.uploadcnab.util.Utils;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class UserFixture {
    private Random random = new Random();

    public UserFixture() {

    }

    public User create() {
        User user = new User();
        user.setRole("USER");
        user.setPassword("123");
        user.setEmail(Utils.randomStringSimple(10));
        user.setId(UUID.randomUUID());

        return user;
    }
}
