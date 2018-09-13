package hackday.bookingservice;

import hackday.bookingservice.model.Booking;
import hackday.bookingservice.model.BookingResponse;
import hackday.bookingservice.model.Customer;
import hackday.bookingservice.model.Flight;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/booking")
public class RestController {

    @Value("${flight.service.url}")
    private String flightServiceURL;

    @Value("${customer.service.url}")
    private String customerServiceURL;

    private HashMap<String, Booking> bookings;

    public RestController() {
        bookings = new HashMap<>();
        bookings.put("1", new Booking(1, 1, 1, new BigDecimal(350)));
        bookings.put("2", new Booking(2, 1, 3, new BigDecimal(150)));
        bookings.put("3", new Booking(3, 2, 1, new BigDecimal(350)));
        bookings.put("4", new Booking(4, 3, 1, new BigDecimal(358)));
        bookings.put("5", new Booking(5, 3, 2, new BigDecimal(122)));
    }

    @GetMapping("")
    @ResponseBody
    public List<BookingResponse> getBooking() {

        RestTemplate restTemplate = new RestTemplate();

        //List<Flight> flightListResponse = (List<Flight>) restTemplate.getForObject(flightServiceURL + "/flight", List.class);
        //List<Customer> customerListResponse = (List<Customer>) restTemplate.getForObject(customerServiceURL + "/customer", List.class);

        ResponseEntity<List<Flight>> flightListResponseEntity = restTemplate.exchange(flightServiceURL + "/flight", HttpMethod.GET, null, new ParameterizedTypeReference<List<Flight>>(){});
        List<Flight> flightListResponse = flightListResponseEntity.getBody();
        ResponseEntity<List<Customer>> customerListResponseEntity = restTemplate.exchange(customerServiceURL + "/customer", HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>(){});
        List<Customer> customerListResponse = customerListResponseEntity.getBody();

        
        List<BookingResponse> bookingList = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setBookingId(booking.getBookingId());
            bookingResponse.setCost(booking.getCost());
            for (Flight flight : flightListResponse) {
                if (flight.getId() == booking.getFlightId()) {
                    bookingResponse.setFlight(flight);
                    break;
                }
            }
            for (Customer cust : customerListResponse) {
                if (cust.getId() == booking.getCustomerId()) {
                    bookingResponse.setCustomer(cust);
                    break;
                }
            }
            bookingList.add(bookingResponse);
        }

        return bookingList;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookingResponse getBookingById(@PathVariable("id") String bookingId) {

        Booking booking = bookings.get(bookingId);

        // Call other services for flight and customer details
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<List<Flight>> flightListResponseEntity = restTemplate.exchange(flightServiceURL + "/flight", HttpMethod.GET, null, new ParameterizedTypeReference<List<Flight>>(){});
        List<Flight> flightListResponse = flightListResponseEntity.getBody();
        ResponseEntity<List<Customer>> customerListResponseEntity = restTemplate.exchange(customerServiceURL + "/customer", HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>(){});
        List<Customer> customerListResponse = customerListResponseEntity.getBody();

        // Construct response
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setCost(booking.getCost());
        for (Flight flight : flightListResponse) {
            if (flight.getId() == booking.getFlightId()) {
                bookingResponse.setFlight(flight);
                break;
            }
        }
        for (Customer cust : customerListResponse) {
            if (cust.getId() == booking.getCustomerId()) {
                bookingResponse.setCustomer(cust);
                break;
            }
        }

        return bookingResponse;
    }

}
