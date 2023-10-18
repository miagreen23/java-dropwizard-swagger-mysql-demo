package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.glassfish.jersey.server.JSONP;

import java.sql.Date;

public class OrderRequest {
    private int customerId;
    private Date orderDate;

    @JsonCreator
    public OrderRequest(
            @JsonProperty("CustomerID") int customerId,
            @JsonProperty("OrderDate") Date orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
