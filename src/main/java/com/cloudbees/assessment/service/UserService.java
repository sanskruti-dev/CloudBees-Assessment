package com.cloudbees.assessment.service;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.models.UserResponse;
import com.cloudbees.assessment.repository.TicketRepository;
import com.cloudbees.assessment.repository.UserRepository;
import com.cloudbees.assessment.util.CloudbeesException;
import com.cloudbees.assessment.util.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<UserResponse> getUsersBySection(String section) throws CloudbeesException {
        if(!DataValidator.validSections.contains(section)) {
            throw new CloudbeesException("Invalid section, Section can be A or B", 400);
        }
        Map<String, Ticket> ticketMap = ticketRepository.getTicketMap();
        List<UserResponse> userResponses = new ArrayList<>();
        ticketMap.values().stream().filter(ticket -> ticket.getSection().equals(section)).forEach(ticket -> {
            userResponses.add(new UserResponse( ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName(),
                    ticket.getUser().getEmail(),ticket.getSeatAllocted(), ticket.getTicketId()));
        });
        return userResponses;
    }
}
