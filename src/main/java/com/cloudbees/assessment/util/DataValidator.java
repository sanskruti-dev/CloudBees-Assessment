package com.cloudbees.assessment.util;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.dtos.User;

import java.util.List;

public class DataValidator {

    public static List<String> validSections  = List.of("A", "B");

    private static void validateEmail(String email) throws CloudbeesException {
        if(!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            throw new CloudbeesException("Invalid email", 400);
        }
    }

    private static void validateName(String name) throws CloudbeesException {
        if(!name.matches("^[a-zA-Z\\s]*$")) {
            throw new CloudbeesException("Invalid name", 400);
        }
    }

    public static void validateTicketDetails(Ticket ticket) throws CloudbeesException {
        if(ticket.getUser() == null) {
            throw new CloudbeesException("User is required", 400);
        }
        User user = ticket.getUser();
        if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new CloudbeesException("First Name is required", 400);
        }
        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new CloudbeesException("Last Name is required", 400);
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new CloudbeesException("Email is required", 400);
        }

        validateEmail(user.getEmail());
        validateName(user.getFirstName());
        validateName(user.getLastName());

        if(ticket.getSection() == null || ticket.getSection().isEmpty()) {
            throw new CloudbeesException("Section is required", 400);
        }
        if(!validSections.contains(ticket.getSection())) {
            throw new CloudbeesException("Invalid section, Section can be A or B", 400);
        }
        if(ticket.getFrom() == null || ticket.getFrom().isEmpty()) {
            throw new CloudbeesException("From Station is required", 400);
        }
        if(ticket.getTo() == null || ticket.getTo().isEmpty()) {
            throw new CloudbeesException("To Station is required", 400);
        }
        if(ticket.getPrice() == null || ticket.getPrice() <= 0) {
            throw new CloudbeesException("Price should be greater than 0", 400);
        }
    }
}
