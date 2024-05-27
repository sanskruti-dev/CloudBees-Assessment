package com.cloudbees.assessment.controllers;

import com.cloudbees.assessment.models.TicketResponse;
import com.cloudbees.assessment.models.UserResponse;
import com.cloudbees.assessment.service.UserService;
import com.cloudbees.assessment.util.CloudbeesException;
import com.cloudbees.assessment.util.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstants.BASE_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(URLConstants.GET_ALL_USERS_BY_SECTION)
    public ResponseEntity<Object> getUsersBySection(@PathVariable String section) {
        try {
            return ResponseEntity.ok().body(userService.getUsersBySection(section));
        } catch (CloudbeesException e) {
            TicketResponse error = new TicketResponse();
            error.setCode(String.valueOf(e.getHttpErrorCode()));
            error.setMessage(e.getMessage());
            return ResponseEntity.status(e.getHttpErrorCode()).body(error);
        }
    }

}
