package com.cloudbees.assessment.repository;

import com.cloudbees.assessment.dtos.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TicketRepositoryTest {

    private TicketRepository ticketRepository;

    @BeforeEach
    public void setup() {
        ticketRepository = new TicketRepository();
    }

    @Test
    public void testSaveAndGetTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("123456");
        ticketRepository.saveTicket(ticket);

        Ticket retrievedTicket = ticketRepository.getTicket("123456");

        assertNotNull(retrievedTicket);
        assertEquals("123456", retrievedTicket.getTicketId());
    }

    @Test
    public void testGetTicketNotFound() {
        Ticket ticket = ticketRepository.getTicket("nonexistent_id");

        assertNull(ticket);
    }

    @Test
    public void testDeleteTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("123456");
        ticketRepository.saveTicket(ticket);

        ticketRepository.deleteTicket("123456");

        Ticket deletedTicket = ticketRepository.getTicket("123456");

        assertNull(deletedTicket);
    }

    @Test
    public void testUpdateTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("123456");
        ticket.setSection("A");
        ticketRepository.saveTicket(ticket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setTicketId("123456");
        updatedTicket.setSection("B");
        ticketRepository.updateTicket(updatedTicket);

        Ticket retrievedTicket = ticketRepository.getTicket("123456");

        assertNotNull(retrievedTicket);
        assertEquals("B", retrievedTicket.getSection());
    }

    @Test
    public void testGetTicketMap() {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId("123456");
        ticketRepository.saveTicket(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("789012");
        ticketRepository.saveTicket(ticket2);

        Map<String, Ticket> ticketMap = ticketRepository.getTicketMap();

        assertNotNull(ticketMap);
        assertEquals(2, ticketMap.size());
        assertTrue(ticketMap.containsKey("123456"));
        assertTrue(ticketMap.containsKey("789012"));
    }
}
