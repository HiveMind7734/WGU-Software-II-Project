/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian
 */
public class DBConnection {

    //JDBC URL parts
//    Server name: 3.227.166.251
//Database name: U06fSJ
//Username: U06fSJ
//Password: 53688753052
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06fSJ";
    private static final String username = "U06fSJ";
    private static final String password = "53688753052";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    public static Connection conn = null;

    public static Connection startConnection() throws SQLException {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection was a success!");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("connection closed");
        } catch (SQLException ex) {

            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
