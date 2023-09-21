package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.User;
import com.wallace.uploadcnab.fixture.UserFixture;
import com.wallace.uploadcnab.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private User user;
    private final UserFixture userFixture;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    public UserServiceTest(){
        this.userFixture = new UserFixture();
    }

    @BeforeEach
    void init() {
        user = userFixture.create();
    }

    @Test
    void should_SaveUser_When_UserServiceSavedCalled() {
        when(userRepository.save(any())).thenReturn(user);

        User userSaved = userService.save(user);

        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertEquals(userSaved.getRole(), user.getRole());
        assertEquals(userSaved.getEmail(), user.getEmail());
        assertEquals(userSaved.getPassword(), user.getPassword());
    }

    @Test
    void should_ReturnUser_When_UserServiceFindByEmailCalled() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Optional<User> response = userService.findEmail(user.getEmail());

        assertTrue(response.isPresent());
        assertNotNull(response);
        assertNotNull(response.get().getId());
        assertEquals(response.get().getRole(), user.getRole());
        assertEquals(response.get().getEmail(), user.getEmail());
        assertEquals(response.get().getPassword(), user.getPassword());
    }

    @Test
    void should_ReturnUserDetail_When_UserServiceFindByEmailCalled() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(userDetails.getAuthorities().size(), 1);
        assertEquals(userDetails.getUsername(), user.getEmail());
    }

    @Test
    void should_ReturnError_When_UserServiceFindByEmailCalled() {
        UsernameNotFoundException error = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("email@email.com");
        }, "User email@email.com dont founded.");

        assertEquals("User email@email.com dont founded.", error.getMessage());
    }
}
