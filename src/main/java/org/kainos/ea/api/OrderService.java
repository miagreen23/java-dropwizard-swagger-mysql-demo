package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.db.OrderDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private OrderValidator orderValidator = new OrderValidator();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {
        try {
            List<Order> orderList = orderDao.getAllOrders();


//       ----- Will sort the orders by order date -----
//            Collections.sort(orderList);
//       orderList.forEach(System.out::println);

//        orderList
//                .stream()
//                .filter(order -> order.getOrderDate().before(Date.from(Instant.now().minus(Duration.ofDays(7)))))
//                .forEach(System.out::println);
//        ----- Will sort the orders by order date -----


//        ----- Will return/print all orders where customer ID is 1 -----
//            for (Order o : orderList) {
//                if (o.getCustomerId() == 1) {
//                    System.out.println(o);
//                }
//            }
//
//            orderList
//                    .stream()
//                    .filter(order -> order.getCustomerId() == 1)
//                    .forEach(System.out::println);
//        ----- Will return/print all orders where customer ID is 1 -----


            // ----- Will count how many orders each customer has made -----
//            Map<Integer, Long> orderMap = orderList
//                    .stream()
//                    .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.counting()));
//
//            System.out.println(orderMap);
//        ----- Will count how many orders each customer has made -----


//        ----- Will then show the customer with most and fewest orders -----
//            System.out.println("Customer with most orders: " + Collections.max(orderMap.entrySet(), Map.Entry.comparingByValue()).getKey());
//            System.out.println("Customer with least orders: " + Collections.min(orderMap.entrySet(), Map.Entry.comparingByValue()).getKey());
//        ----- Will then show the customer with most and fewest orders -----

            return orderList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrdersException();
        }
    }

    public Order getOrderById(int id) throws FailedToGetOrdersException, OrderDoesNotExistException {
        try {
            Order order = orderDao.getOrderById(id);

            if (order == null) {
                throw new OrderDoesNotExistException();
            }
            return order;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetOrdersException();
        }
    }

    public int createOrder(OrderRequest order) throws FailedToCreateOrderException, InvalidOrderException {
        try {
            String validation = orderValidator.isValidOrder(order);

            if (validation != null) {
                throw new InvalidOrderException(validation);
            }

            int id = orderDao.createOrder(order);

            if (id == -1) {
                throw new FailedToCreateOrderException();
            }
            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateOrderException();
        }
    }

    public void updateOrder(int id, OrderRequest order) throws InvalidOrderException, OrderDoesNotExistException, FailedToUpdateOrderException {
        try {
            String validation = orderValidator.isValidOrder(order);

            if (validation != null) {
                throw new InvalidOrderException(validation);
            }

            Order orderToUpdate = orderDao.getOrderById(id);

            if (orderToUpdate == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.updateOrder(id, order);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateOrderException();
        }
    }

    public void deleteOrder(int id) throws OrderDoesNotExistException, FailedToDeleteOrderException {
        try {
            Order orderToDelete = orderDao.getOrderById(id);

            if (orderToDelete == null) {
                throw new OrderDoesNotExistException();
            }
            orderDao.deleteOrder(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteOrderException();
        }
    }
}
