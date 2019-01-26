package com.example.asus.findyourhomemate;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    static Connection con = null;
    static String serverAddress, database, dbUser, dbPassword, connectionUrl;

    public static Connection  createConnection() {
        serverAddress = "den1.mssql7.gear.host";
        database = "findyourhomemate";
        dbUser = "findyourhomemate";
        dbPassword = "Uw3rDlm_4!vn";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionUrl= "jdbc:jtds:sqlserver://"+serverAddress+";database="+database+";user="+dbUser+";password="+dbPassword+";";
                con = DriverManager.getConnection(connectionUrl);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return con;
    }
}
