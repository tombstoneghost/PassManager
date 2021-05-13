package Core;

import java.sql.*;

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

    boolean SaveUser(String firstName, String lastName, String email, String username, String password) {
        OpenConnection();

        String SAVE_USER_STATEMENT = "INSERT INTO Users(FirstName, LastName, EMail, Username, MasterKey) " +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement saveUser = connection.prepareStatement(SAVE_USER_STATEMENT);

            saveUser.setString(1, firstName);
            saveUser.setString(2, lastName);
            saveUser.setString(3, email);
            saveUser.setString(4, username);
            saveUser.setString(5, password);

            int result = saveUser.executeUpdate();

            saveUser.close();

            if(result > 0) {
                CloseConnection();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
            CloseConnection();
            e.printStackTrace();
        }

        CloseConnection();

        return false;
    }

    boolean checkUser(String username) {
        OpenConnection();
        String CHECK_USER_EXIST = "SELECT Username FROM Users WHERE Username=(?)";

        try {
            PreparedStatement checkUserStatement = connection.prepareStatement(CHECK_USER_EXIST);

            checkUserStatement.setString(1, username);

            ResultSet result = checkUserStatement.executeQuery();

            if(result.getString("Username").equals(username)) {
                CloseConnection();
                return true;
            }

            result.close();
            checkUserStatement.close();


        } catch (SQLException e) {
            CloseConnection();
            System.out.println("SQL Exception: " + e);
            e.printStackTrace();
        }

        CloseConnection();

        return false;
    }

    boolean verifyUser(String username, String password) {
        OpenConnection();

        String VERIFY_USER_LOGIN = "SELECT * FROM Users WHERE Username=(?)";

        try {
            PreparedStatement userLogin = connection.prepareStatement(VERIFY_USER_LOGIN);

            userLogin.setString(1, username);

            ResultSet resultSet = userLogin.executeQuery();

            String passwordDB = resultSet.getString("MasterKey");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");

            resultSet.close();
            userLogin.close();

            Security security = new Security(firstName, lastName, username);

            password = security.EncryptPassword(password);

            if(passwordDB.equals(password)) {
                CloseConnection();
                return true;
            } else {
                CloseConnection();
                return false;
            }


        } catch (SQLException e) {
            CloseConnection();
            System.out.println("SQL Exception: " + e);
            e.printStackTrace();
        }

        CloseConnection();
        return false;
    }


    public String getEMail(String User) {
        OpenConnection();

        try {
            String GET_EMAIL = "SELECT EMail FROM Users WHERE Username = ?";

            PreparedStatement getEMailStmt = connection.prepareStatement(GET_EMAIL);

            getEMailStmt.setString(1, User);

            ResultSet result = getEMailStmt.executeQuery();

            String email = result.getString("EMail");

            CloseConnection();

            return email;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();

        return "";
    }
}
