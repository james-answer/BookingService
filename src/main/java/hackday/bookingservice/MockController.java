package hackday.bookingservice;

import hackday.bookingservice.model.Customer;
import hackday.bookingservice.model.Flight;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/mock")
public class MockController {
    
    @Value("${flight.service.url}")
    private String flightServiceURL;
    
    @Value("${customer.service.url}")
    private String customerServiceURL;
    
    @GetMapping("/flight")
	@ResponseBody
	public List<Flight> getFlights() {

        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1, "LB", "PAR"));
        flights.add(new Flight(2, "LB", "AD"));
        return flights;
	}
    
    @GetMapping("/customer")
	@ResponseBody
	public List<Customer> getCustomers() {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Bob", "Smith"));
        customers.add(new Customer(2, "Timmy", "D"));
        return customers;
	}
    
}
