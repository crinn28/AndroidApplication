package com.example.crimeslostsreport.Classes.Database;

import com.example.crimeslostsreport.Models.Status;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBStatus extends DBHelper {

    Connection connection;

    public DBStatus() {
        connection = super.connection;
    }

    public Status getStatus(int statusId) {
        Status status = new Status();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_status(?)");
            statement.setInt(1, statusId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                status = new Status(result.getInt("status_id"),
                        result.getString("status_name"));
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return status;
    }

    public ArrayList<Status> getStatuses() {
        ArrayList<Status> statuses = new ArrayList<>();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_statuses()");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Status status = new Status(result.getInt("status_id"),
                        result.getString("status_name"));
                if (!status.getStatusName().equals("new")) {
                    status.setStatusName(status.getStatusName().toUpperCase());
                    statuses.add(status);
                }
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return statuses;
    }
}
