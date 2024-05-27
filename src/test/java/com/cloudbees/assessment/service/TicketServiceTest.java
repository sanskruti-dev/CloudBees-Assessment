package com.cloudbees.assessment.service;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.dtos.User;
import com.cloudbees.assessment.models.TicketResponse;
import com.cloudbees.assessment.repository.TicketRepository;
import com.cloudbees.assessment.repository.TicketRepositoryTest;
import com.cloudbees.assessment.util.CloudbeesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmitTicket() throws CloudbeesException {
        Ticket ticket = new Ticket();
        ticket.setSection("A");
        ticket.setUser(new User("John", "Doe", "johndoe@gmail.com"));
        ticket.setFrom("New York");
        ticket.setTo("Los Angeles");
        ticket.setPrice(100l);
        doNothing().when(ticketRepository).saveTicket(ticket);

        TicketResponse response = ticketService.submitTicket(ticket);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("Ticket submitted successfully", response.getMessage());
        assertNotNull(response.getTicketId());
    }

    @Test
    public void testGetTicket() throws CloudbeesException {
        String ticketId = "123456";
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);

        when(ticketRepository.getTicket(ticketId)).thenReturn(ticket);

        Ticket retrievedTicket = ticketService.get(ticketId);

        assertNotNull(retrievedTicket);
        assertEquals(ticketId, retrievedTicket.getTicketId());
    }

    @Test
    public void testDeleteTicket() throws CloudbeesException {
        String ticketId = "123456";
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);

        when(ticketRepository.getTicket(ticketId)).thenReturn(ticket);

        doNothing().when(ticketRepository).deleteTicket(ticketId);

        ticketService.delete(ticketId);
    }

    @Test
    public void testUpdateTicket() throws CloudbeesException {
        String ticketId = "123456";
        Ticket ticket = new Ticket();
        ticket.setSection("A");

        Ticket existingTicket = new Ticket();
        existingTicket.setTicketId(ticketId);
        existingTicket.setSection("B");

        when(ticketRepository.getTicket(ticketId)).thenReturn(existingTicket);
        doNothing().when(ticketRepository).updateTicket(existingTicket);
        doNothing().when(ticketRepository).updateTicket(existingTicket);

        assertDoesNotThrow(() -> ticketService.update(ticketId, ticket));
        assertEquals("A", existingTicket.getSection());
    }

    @Test
    public void testSubmitTicketWithInvalidSection() {
        Ticket ticket = new Ticket();
        ticket.setSection("C"); // Invalid section

        assertThrows(CloudbeesException.class, () -> ticketService.submitTicket(ticket));
    }

    @Test
    public void testGetTicketWithNullId() {
        String ticketId = null;

        assertThrows(CloudbeesException.class, () -> ticketService.get(ticketId));
    }

    @Test
    public void testDeleteTicketWithNullId() {
        String ticketId = null;

        assertThrows(CloudbeesException.class, () -> ticketService.delete(ticketId));
    }

    @Test
    public void testUpdateTicketWithNullId() {
        String ticketId = null;
        Ticket ticket = new Ticket();
        ticket.setSection("A");

        assertThrows(CloudbeesException.class, () -> ticketService.update(ticketId, ticket));
    }
}
