package org.example;

import org.apache.log4j.BasicConfigurator;

import java.sql.*;
import static spark.Spark.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/dolls";
    private static final String username = "root";
    private static final String password = "topsecretpassword";
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        //BasicConfigurator.configure();
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println(connection);

            get("/dolls", (req, res) -> {
                getAllDolls();
                return "Done";
            });
            post("/dolls/:name/:price/:stock", (req, res) -> {
                insertDoll(req.params(":name"), Double.parseDouble(req.params(":price")), Integer.parseInt(req.params(":stock")));
                return "Done insert";
            });
            post("/dolls/delete/:name", (req, res) -> {
                deleteDoll(req.params(":name"));
                return "Done delete";
            });
            put("/dolls/update/:name/:price/:stock", (req, res) -> {
                updateDoll(req.params(":name"), Double.parseDouble(req.params(":price")), Integer.parseInt(req.params(":stock")));
                return "Done update";
            });
            get("/dolls/:id", (req, res) -> {
                getDollById(Integer.parseInt(req.params(":id")));
                return "Done";
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getAllDolls() {
        Statement ps = null;
        try {
            ps = connection.createStatement();
            ResultSet rs = ps.executeQuery("SELECT * FROM doll;");
            while (rs.next()) {
                Doll d = new Doll(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
                System.out.println(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void getDollById(int id) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM doll WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Doll d = new Doll(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
                System.out.println(d);
            }
            else {
                System.out.println("Doll doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertDoll(String name, double price, int stock) {
        PreparedStatement ps2 = null;
        try {
            ps2 = connection.prepareStatement("INSERT INTO doll (name, price, stock) VALUES ( ?, ?, ?);");
            ps2.setString(1, name);
            ps2.setDouble(2, price);
            ps2.setInt(3, stock);
            ps2.execute();
        } catch (SQLException e) {
            e.printStackTrace();;
        }

    }

    private static void deleteDoll(String name) {
        PreparedStatement ps2 = null;
        try {
            ps2 = connection.prepareStatement("DELETE from doll WHERE name=?");
            ps2.setString(1, name);
            ps2.execute();
        } catch (SQLException e) {
            e.printStackTrace();;
        }

    }

    private static void updateDoll(String name, double price, int stock) {
        PreparedStatement ps2 = null;
        try {
            ps2 = connection.prepareStatement("UPDATE doll SET price = ?, stock = ? WHERE name = ?;");
            ps2.setDouble(1, price);
            ps2.setInt(2, stock);
            ps2.setString(3, name);
            ps2.execute();
        } catch (SQLException e) {
            e.printStackTrace();;
        }

    }
}