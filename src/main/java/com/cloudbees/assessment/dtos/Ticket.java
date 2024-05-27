package com.cloudbees.assessment.dtos;

public class Ticket {
    private String from;
    private String to;
    private Long price;
    private String section;
    private String seatAllocted;
    private String ticketId;
    private User user;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSeatAllocted() {
        return seatAllocted;
    }

    public void setSeatAllocted(String seatAllocted) {
        this.seatAllocted = seatAllocted;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
