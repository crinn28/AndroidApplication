package com.example.crimeslostsreport.Classes.Database;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.AnnouncementLocation;
import com.example.crimeslostsreport.Models.SecretCode;
import com.example.crimeslostsreport.Models.Status;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.Models.UserRole;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DBHelper {
    Connection connection;

    public DBHelper() {
        connection = createConnection();
    }

    @SuppressLint("NewApi")
    public Connection createConnection() {
        String ip = "192.168.100.41", port = "1433", db = "Crimes&Losts", username = "root", password = "crina";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + db + ";user=" + username + ";" + "password=" + password + ";";
            connection = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
        }
        return connection;
    }
}


