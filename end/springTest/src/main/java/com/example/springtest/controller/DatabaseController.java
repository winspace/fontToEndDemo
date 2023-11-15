package com.example.springtest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DatabaseController {


    @GetMapping("/data")
    public List<String> getData() {
        List<String> result = new ArrayList<>();

        // Replace the following connection details with your actual database connection information
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "123456";

        String query = "SELECT name FROM testtable";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String data = resultSet.getString("name");
                result.add(data);
            }

        } catch (SQLException e) {
            // Handle any potential exceptions here
//            e.printStackTrace();
        }

        return result;
    }
}