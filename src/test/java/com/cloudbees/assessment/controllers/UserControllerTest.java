package com.cloudbees.assessment.controllers;

import com.cloudbees.assessment.models.UserResponse;
import com.cloudbees.assessment.service.UserService;
import com.cloudbees.assessment.util.CloudbeesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsersBySection() throws CloudbeesException {
        String section = "A";
        List<UserResponse> expectedUsers = Collections.singletonList(new UserResponse("John", "Doe", "A12", "1234"));

        when(userService.getUsersBySection(section)).thenReturn(expectedUsers);

        ResponseEntity<Object> responseEntity = userController.getUsersBySection(section);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUsers, responseEntity.getBody());
    }

    @Test
    public void testGetUsersBySectionWithException() throws CloudbeesException {
        String section = "A";
        CloudbeesException exception = new CloudbeesException("Section not found", HttpStatus.NOT_FOUND.value());

        when(userService.getUsersBySection(section)).thenThrow(exception);

        ResponseEntity<Object> responseEntity = userController.getUsersBySection(section);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
