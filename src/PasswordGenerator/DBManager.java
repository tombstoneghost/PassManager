package PasswordGenerator;

import java.sql.*;
import java.util.Hashtable;

public class DBManager {

    private Connection connection;

    void OpenConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            String CONNECTION_STRING = "jdbc:sqlite:D:/Projects/Java/PassManager/src/PassManager.db";
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e);
            e.printStackTrace();
        }
    }

    void CloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }

    public boolean SaveUserPII(Hashtable<String, String> UserPII, String username) {
        OpenConnection();

        try {
            String SAVE_USER_PII = "INSERT INTO UserPII(username, FirstName, LastName, BirthDay, BirthMonth, BirthYear, EMail, " +
                    "HouseNumber, City, State, Country, PhoneNumber, CollegeName, CarNumber) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement saveUserPII = connection.prepareStatement(SAVE_USER_PII);

            saveUserPII.setString(1, username);
            saveUserPII.setString(2, UserPII.get("FirstName"));
            saveUserPII.setString(3, UserPII.get("LastName"));
            saveUserPII.setString(4, UserPII.get("BirthDay"));
            saveUserPII.setString(5, UserPII.get("BirthMonth"));
            saveUserPII.setString(6, UserPII.get("BirthYear"));
            saveUserPII.setString(7, UserPII.get("EMail"));
            saveUserPII.setString(8, UserPII.get("HouseNumber"));
            saveUserPII.setString(9, UserPII.get("City"));
            saveUserPII.setString(10, UserPII.get("State"));
            saveUserPII.setString(11, UserPII.get("Country"));
            saveUserPII.setString(12, UserPII.get("PhoneNumber"));
            saveUserPII.setString(13, UserPII.get("CollegeName"));
            saveUserPII.setString(14, UserPII.get("CarNumber"));

            int res = saveUserPII.executeUpdate();

            if(res > 0) {
                CloseConnection();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }

        CloseConnection();
        return false;
    }

    public boolean CheckUserInDB(String username) {
        OpenConnection();

        try {
            String CHECK_USER_IN_DB = "SELECT username FROM UserPII WHERE username = ?";

            PreparedStatement checkUserInDB = connection.prepareStatement(CHECK_USER_IN_DB);

            checkUserInDB.setString(1, username);

            ResultSet res = checkUserInDB.executeQuery();

            if(res.getString("username").equals(username)) {
                CloseConnection();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("User PII not in Database.");
        }

        CloseConnection();
        return false;
    }

    public Hashtable<String, String> GetUserPII(String username) {
        OpenConnection();

        Hashtable<String, String> userPII = new Hashtable<>();

        try {
            String GET_USER_PII = "SELECT * FROM UserPII WHERE username = ?";

            PreparedStatement getUserPII = connection.prepareStatement(GET_USER_PII);

            getUserPII.setString(1, username);

            ResultSet resultSet = getUserPII.executeQuery();

            userPII.put("FirstName", resultSet.getString("FirstName"));
            userPII.put("LastName", resultSet.getString("LastName"));
            userPII.put("BirthDay", resultSet.getString("BirthDay"));
            userPII.put("BirthMonth", resultSet.getString("BirthMonth"));
            userPII.put("BirthYear", resultSet.getString("BirthYear"));
            userPII.put("EMail", resultSet.getString("EMail"));
            userPII.put("HouseNumber", resultSet.getString("HouseNumber"));
            userPII.put("City", resultSet.getString("City"));
            userPII.put("State", resultSet.getString("State"));
            userPII.put("Country", resultSet.getString("Country"));
            userPII.put("PhoneNumber", resultSet.getString("PhoneNumber"));
            userPII.put("CollegeName", resultSet.getString("CollegeName"));
            userPII.put("CarNumber", resultSet.getString("CarNumber"));

            CloseConnection();
            return userPII;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }

        CloseConnection();
        return userPII;
    }
}
