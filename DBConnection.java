package sample;

import java.sql.*;

public class DBConnection {

    private static String user = "postgres"; // username pentru baza ta de date
    private static String password = "Billyboy76"; // parola pentru baza ta de date
    private static String dbUrl = "jdbc:postgresql:todolist"; // lincul catre baza ta de date

    // functie care ne returneaza o conexiune la baza de date
    public static Connection getConnection() {

        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("    [INFO] connection successfull");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myConn;

    }
}
