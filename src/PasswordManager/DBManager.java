package PasswordManager;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean AddNewCred(String user, String Application, String Username, String Password) {
        OpenConnection();
        String ADD_NEW_CREDENTIALS = "INSERT INTO Credentials(User, Application, Username, Password) VALUES(?,?,?,?)";

        try {
            PreparedStatement addNewCredentials = connection.prepareStatement(ADD_NEW_CREDENTIALS);

            addNewCredentials.setString(1, user);
            addNewCredentials.setString(2, Application);
            addNewCredentials.setString(3, Username);
            addNewCredentials.setString(4, Password);

            int res = addNewCredentials.executeUpdate();

            CloseConnection();
            return res > 0;
        } catch (SQLException e) {
            CloseConnection();
            System.out.println("SQLException: " + e);
            return false;
        }
    }

    public boolean CheckUserApplications(String user) {
        OpenConnection();

        try {
            String CHECK_USER_IN_APPS = "SELECT User FROM Credentials WHERE User = ?";

            PreparedStatement checkUser = connection.prepareStatement(CHECK_USER_IN_APPS);
            checkUser.setString(1, user);

            ResultSet resultSet = checkUser.executeQuery();

            if(resultSet.getString("User").equals(user)) {
                CloseConnection();
                return true;
            } else {
                CloseConnection();
                return false;
            }

        } catch (SQLException ignored) {

        }

        CloseConnection();
        return false;
    }

    public ArrayList<String> ListApplicationCreds(String user) {
        OpenConnection();

        String LIST_APPLICATIONS = "SELECT Application FROM Credentials WHERE User = ?";

        ArrayList<String> apps = new ArrayList<>();

        try {
            PreparedStatement listApps = connection.prepareStatement(LIST_APPLICATIONS);

            listApps.setString(1, user);

            ResultSet result = listApps.executeQuery();

            while(result.next()) {
                apps.add(result.getString("Application"));
            }

            CloseConnection();

            return apps;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();

        return apps;
    }

    public String[] GetCred(String User, String Application) {
        OpenConnection();

        String GET_APP_CREDENTIALS = "SELECT * FROM Credentials WHERE User = ? AND APPLICATION = ?";

        String username, password;

        String[] Details = new String[2];

        try {
            PreparedStatement getCredentials = connection.prepareStatement(GET_APP_CREDENTIALS);

            getCredentials.setString(1, User);
            getCredentials.setString(2, Application);

            ResultSet result = getCredentials.executeQuery();

            username = result.getString("Username");
            password = result.getString("Password");

            Security security = new Security(Application, username, User);

            password = security.DecryptPassword(password);

            Details[0] = username;
            Details[1] = password;

            CloseConnection();

            return Details;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();
        return Details;
    }

    public boolean UpdateApplicationCredentials(String user, String application, String newPassword) {
        OpenConnection();

        try {
            String UPDATE_APP_CREDS = "UPDATE Credentials SET Password = ? WHERE User = ? AND Application = ?";

            PreparedStatement updateCred = connection.prepareStatement(UPDATE_APP_CREDS);

            updateCred.setString(1, newPassword);
            updateCred.setString(2, user);
            updateCred.setString(3, application);

            int res = updateCred.executeUpdate();

            if(res > 0) {
                CloseConnection();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();
        return false;
    }

    public boolean DeleteApplication(String user, String application) {
        OpenConnection();

        try {
            String DELETE_APP_CREDS = "DELETE FROM Credentials WHERE User = ? AND Application = ?";


            PreparedStatement deleteAppCreds = connection.prepareStatement(DELETE_APP_CREDS);

            deleteAppCreds.setString(1, user);
            deleteAppCreds.setString(2, application);

            int res = deleteAppCreds.executeUpdate();

            System.out.println("Res: " + res);

            if(res > 0) {
                CloseConnection();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();
        return false;
    }

    public String getApplicationUsername(String user, String application) {
        OpenConnection();

        String Username = "";

        try {
            String GET_APP_UNAME = "SELECT Username FROM Credentials WHERE User = ? AND Application = ?";

            PreparedStatement app_uname = connection.prepareStatement(GET_APP_UNAME);

            app_uname.setString(1, user);
            app_uname.setString(2, application);

            ResultSet result = app_uname.executeQuery();

            Username = result.getString("Username");

            CloseConnection();

            return Username;

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }

        CloseConnection();
        return Username;
    }
}
