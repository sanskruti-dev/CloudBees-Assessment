package com.cloudbees.assessment.service;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.dtos.User;
import com.cloudbees.assessment.models.UserResponse;
import com.cloudbees.assessment.repository.TicketRepository;
import com.cloudbees.assessment.util.CloudbeesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsersBySection() throws CloudbeesException {
        String section = "A";

        // Mock ticket repository
        Map<String, Ticket> ticketMap = new HashMap<>();
        Ticket ticket1 = new Ticket();
        ticket1.setUser(new User("John", "Doe", "john@gmail.com"));
        ticket1.setSeatAllocted("A1");
        ticket1.setTicketId("123456");
        ticket1.setSection("A");

        Ticket ticket2 = new Ticket();
        ticket2.setUser(new User("ssn", "dwd", "ssn@gmail.com"));
        ticket2.setSeatAllocted("A2");
        ticket2.setTicketId("789012");
        ticket2.setSection("A");

        ticketMap.put(ticket1.getTicketId(), ticket1);
        ticketMap.put(ticket2.getTicketId(), ticket2);

        when(ticketRepository.getTicketMap()).thenReturn(ticketMap);

        // Test getUsersBySection
        List<UserResponse> userResponses = userService.getUsersBySection(section);

        assertNotNull(userResponses);
        assertEquals(2, userResponses.size());
    }

    @Test
    public void testGetUsersByInvalidSection() {
        String invalidSection = "C";

        assertThrows(CloudbeesException.class, () -> userService.getUsersBySection(invalidSection));
    }
}
