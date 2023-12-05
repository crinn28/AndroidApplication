package com.example.crimeslostsreport.Classes.Database;

import com.example.crimeslostsreport.Models.SecretCode;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.Models.UserRole;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBUser extends DBHelper {

    Connection connection;

    public DBUser() {
        connection = super.connection;
    }

    public User Login(String username, String password) {

        User user = new User();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_user(?,?)");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("user_id");
                user = new User(id, result.getString("username"),
                        result.getString("email"),
                        result.getString("password"), getUserRole(result.getInt("role_id")));
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return user;
    }

    public void addUser(User user) {
        try {
            CallableStatement statement = this.connection.prepareCall("call add_user(?, ?, ?, ?)");

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserRole().getRoleId());

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public User getUserById(int user_id) {
        User user = new User();
        try {
            CallableStatement statement = this.connection.prepareCall("call get_user_by_id(?)");

            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user = new User(result.getInt("user_id"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        getUserRole(result.getInt("role_id")));
            }
            statement.close();


        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByUsername(String username) {
        User user = new User();
        try {
            CallableStatement statement = this.connection.prepareCall("call get_user_by_username(?)");

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user = new User(result.getInt("user_id"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        getUserRole(result.getInt("role_id")));
            }
            statement.close();


        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return user;
    }

    public void updatePassword(User user, String password) {
        try {
            CallableStatement statement =
                    this.connection.prepareCall("call update_password(?, ?)");

            statement.setInt(1, user.getUserId());
            statement.setString(2, password);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public void updateEmail(User user, String email) {
        try {
            CallableStatement statement =
                    this.connection.prepareCall("call update_email(?, ?)");

            statement.setInt(1, user.getUserId());
            statement.setString(2, email);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_all_usernames()");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String username = result.getString("username");
                usernames.add(username);
            }
            statement.close();

        } catch (SQLException ex) {
            System.out.print("Error!");
            ex.printStackTrace();
        }
        return usernames;

    }

    public UserRole getUserRole(int userRole) {
        UserRole role = new UserRole();
        try {
            CallableStatement statement = this.connection.prepareCall("call get_user_role(?)");
            statement.setInt(1, userRole);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                role = new UserRole(result.getInt("role_id"), result.getString("name"));
            }

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
        return role;
    }

    public SecretCode getSecretcode(String secretcode) {

        SecretCode secretCode = new SecretCode();
        try {
            CallableStatement statement = this.connection.prepareCall("call get_secretcode(?)");
            statement.setString(1, secretcode);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                secretCode = new SecretCode(result.getInt("secretcode_id"),
                        result.getString("secretcode"));
            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }
        return secretCode;
    }

    public ArrayList<UserRole> getAllRoles() {
        ArrayList<UserRole> roles = new ArrayList<>();

        try {
            CallableStatement statement = this.connection.prepareCall("call get_all_roles()");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UserRole role = new UserRole(result.getInt("role_id"),
                        result.getString("name"));
                roles.add(role);

            }
            statement.close();

        } catch (SQLException e) {
            System.out.print("Error!");
            e.printStackTrace();
        }

        return roles;
    }
}
