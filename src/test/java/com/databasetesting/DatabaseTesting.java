/*
 Purpose : Program for Test Cases is Written to Test Database
 @author Dinesh Kumar Peddakotla
 @since 23-05-2021
*/
package com.databasetesting;

import org.testng.Assert;

import org.testng.annotations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTesting {
    /** declared static sql interface variables */
    static Connection connection;
    static Statement statement;
    /** declared string variable */
    static String sqlQuery;
    /** declared and initialized constant variables */
    public static String DB_URL = "jdbc:mysql://localhost/employee_data?useSSL=false";
    public static String DB_USERNAME = "root";
    public static String DB_PASSWORD = "Dinnu@247";

    /**
     * setup is run before a test to establish a connection with database
     *try and catch blocks are added to throw sql and ClassNotFoundException exception
     */
    @BeforeTest
    public void setUp() {
        try {
            //created a database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            //get connection to database
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            //statement object is created to send request to database
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * test case is written to test when a new data is inserted into database by using sql query
     * try and catch blocks are added to throw sql exception
     */
    @Test
    public void test_WhenNewDataIs_InsertedIntoTable_OfDatabase() {
        try {
            sqlQuery = "insert into employee_data (employee_id, employee_name, mobile_number, salary) " +
                       "values(1, 'dinesh', '8919105923', 40000.00), (2, 'pavan', '9542409463', 30000.00)," +
                       "(3, 'murali', '8919502395', 35000.00);";

            int noOfRowsAffected = statement.executeUpdate(sqlQuery);

            //assertion the number of employees data is added to database
            Assert.assertEquals(noOfRowsAffected, 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * test case is written to test when a data is updated into database by using sql query
     * try and catch blocks are added to throw sql exception
     */
    @Test
    public void test_WhenDataIs_UpdatedIntoTable_OfDatabase() {
        try {
            sqlQuery = "update employee_data set mobile_number = '9545632584' where employee_name = 'murali';";

            int noOfRowsAffected = statement.executeUpdate(sqlQuery);

            //assertion the query is executed or not
            Assert.assertEquals(noOfRowsAffected, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * test case is written to test when a data is retrieved from database by using sql query
     * try and catch blocks are added to throw sql exception
     */
    @Test
    public void test_WhenAllData_IsRetrieved_FromATable_OfDatabase() {
        try {
            List<Object[]> retrievedData = new ArrayList<>();

            sqlQuery = "select * from employee_data";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String mobile_number = resultSet.getString(3);
                double salary = resultSet.getDouble(4);

                System.out.println(id+" "+name+" "+mobile_number+" "+salary);

                Object[] set = new Object[]{id, name, mobile_number, salary};
                retrievedData.add(set);
            }

            //assertion the number of employees data is retrieved
            Assert.assertEquals(retrievedData.size(), 3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * test case is written to test when a data is deleted from database by using sql query
     * try and catch blocks are added to throw sql exception
     */
    @Test
    public void test_WhenAData_IsDeleted_FromATable_OfDatabase() {
        try {
            sqlQuery = "delete from employee_data where employee_name = 'murali'";

            int noOfRowsAffected = statement.executeUpdate(sqlQuery);

            //assertion the query is executed or not
            Assert.assertEquals(noOfRowsAffected, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * teardown is run after a test to disconnect a connection with database
     *try and catch blocks are added to throw sql exception
     */
    @AfterTest
    public void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
