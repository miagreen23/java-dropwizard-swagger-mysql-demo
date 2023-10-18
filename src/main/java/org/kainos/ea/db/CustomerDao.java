package org.kainos.ea.db;

import org.kainos.ea.cli.Customer;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class CustomerDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Customer getCustomerById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT CustomerID, Name FROM Customer WHERE CustomerID = " +id);

        while (rs.next()) {
            return new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("Name")
            );
        }
        return null;
    }
}
