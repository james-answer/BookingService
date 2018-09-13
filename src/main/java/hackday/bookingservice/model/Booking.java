package hackday.bookingservice.model;

import java.math.BigDecimal;

public class Booking {

    private int bookingId;
    private long customerId;
    private long flightId;
    private BigDecimal cost;

    public Booking(){}
    public Booking(int bid, long c, long f, BigDecimal cst){
        bookingId = bid;
        customerId = c;
        flightId = f;
        cost = cst;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
