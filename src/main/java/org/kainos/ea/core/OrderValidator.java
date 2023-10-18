package org.kainos.ea.core;

import org.checkerframework.checker.units.qual.C;
import org.kainos.ea.cli.Customer;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.db.CustomerDao;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class OrderValidator {
    CustomerDao customerDao = new CustomerDao();
    public String isValidOrder(OrderRequest order) throws SQLException {
        // Customer ID is invalid (e.g. customer ID is 0 or minus number)
        if (order.getCustomerId() < 1) {
            return "CustomerID is invalid";
        }

        // Checks if order date is older than 365 days, returns error 400 if it does not exist
        if (order.getOrderDate().before(Date.from(Instant.now().minus(Duration.ofDays(365))))) {
            return "Order date is older than 365 days";
        }

        // Checks if customer ID exists in DB, returns error 400 if it does not exist
        if (customerDao.getCustomerById(order.getCustomerId()) == null) {
            return "Customer ID does not exist";
        }
        return null;
    }
}
