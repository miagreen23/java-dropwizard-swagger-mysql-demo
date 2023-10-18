package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDao {
private DatabaseConnector databaseConnector = new DatabaseConnector();
    public List<Order> getAllOrders() throws SQLException {
        Connection c = DatabaseConnector.getConnection();
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate FROM `Order`;");

            List<Order> orderList = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("OrderDate")
                );
                orderList.add(order);
            }
            return orderList;
        }


        public Order getOrderById(int id) throws SQLException {
            Connection c = databaseConnector.getConnection();
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate"
                    + " FROM `Order` WHERE OrderID = " +id);

            while (rs.next()) {
                return new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getTimestamp("OrderDate")
                );
            }
            return null;

    }

    public int createOrder(OrderRequest order) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO `Order` (CustomerID, OrderDate) VALUES (?, ?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, order.getCustomerId());
        st.setDate(2, order.getOrderDate());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }
    return -1;
    }

    public void updateOrder(int id, OrderRequest order) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String updateStatement = "UPDATE `Order` SET CustomerID = ?, OrderDate = ? WHERE OrderID = ?";

        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setInt(1, order.getCustomerId());
        st.setDate(2, order.getOrderDate());
        st.setInt(3, id);

        st.executeUpdate();
    }

    public void deleteOrder(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String deleteStatement = "DELETE FROM `Order` WHERE OrderID = ?";

        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1, id);

        st.executeUpdate();
    }
}

