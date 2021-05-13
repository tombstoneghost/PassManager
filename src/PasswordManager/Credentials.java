package PasswordManager;

import java.util.Scanner;

public class Credentials {
    DBManager dbManager;

    Credentials() {
        dbManager = new DBManager();
    }

    public boolean Add(String User) {
        Scanner sc = new Scanner(System.in);

        String Application="", Username, Password;

        System.out.println("Enter Application Name: ");
        if(sc.hasNextLine()) {
            Application = sc.nextLine();
        }

        System.out.println("Enter Username: ");
        Username = sc.nextLine();

        System.out.println("Enter Password: ");
        Password = sc.nextLine();

        Security security = new Security(Application, Username, User);

        Password = security.EncryptPassword(Password);

        return dbManager.AddNewCred(User, Application, Username, Password);
    }

    public void Get(String User, String Application) {
        System.out.println("\nHere are the credentials: ");
        System.out.println("Application: " + Application);
        System.out.println("Username: " + dbManager.GetCred(User, Application)[0]);
        System.out.println("Password: " + dbManager.GetCred(User, Application)[1]);
    }

    public boolean Update(String User) {
        Scanner sc = new Scanner(System.in);

        String Application, Password, ConfirmPassword;

        System.out.println("Enter Application Name: ");
        Application = sc.nextLine();

        System.out.println("Enter New Password");
        Password = sc.nextLine();

        System.out.println("Confirm New Password");
        ConfirmPassword = sc.nextLine();

        String Username = dbManager.getApplicationUsername(User, Application);

        if(Password.equals(ConfirmPassword)) {
            Password = new Security(Application, Username, User).EncryptPassword(Password);
            return dbManager.UpdateApplicationCredentials(User, Application, Password);
        } else {
            System.out.println("Passwords does not match");
        }
        return false;
    }

    public boolean Delete(String User) {
        Scanner sc = new Scanner(System.in);

        String Application;

        System.out.println("Enter Application Name: ");
        Application = sc.nextLine();

        return dbManager.DeleteApplication(User, Application);
    }
}
