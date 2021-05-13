package Core;

import PasswordGenerator.InitGenerator;
import PasswordManager.InitManager;

import java.util.Scanner;

public class UserManagement {
    DBManager dbManager;

    private String verifiedUser  = "";
    private int OTP = 0;
    private boolean isVerified;

    public UserManagement() {
        dbManager = new DBManager();

        GenerateOTP();
    }

    private void GenerateOTP() {
        OTP = (int)(Math.random() * (1000000 - 100000 + 1) + 100000);
    }


    private boolean checkOTP() {
        int receivedOTP;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter OTP: ");
        receivedOTP = sc.nextInt();


        return receivedOTP == OTP;
    }

    private boolean VerifyOTP() {

        SMTP smtp = new SMTP();

        String userEMail = dbManager.getEMail(verifiedUser);

        if(smtp.sendMail(OTP, userEMail)) {
            if(checkOTP()) {
                System.out.println("Verified");
                this.isVerified = true;

                return true;
            } else {
                System.out.println("OTP Verification failed.");
            }
        }

        return false;
    }

    public boolean CreateNewUser() {
        Scanner sc = new Scanner(System.in);

        String firstName = "", lastName, username, email, masterKey;

        System.out.println("Enter your First Name: ");
        if(sc.hasNextLine()) {
            firstName = sc.nextLine();
        }

        System.out.println("Enter your Last Name: ");
        lastName = sc.nextLine();

        System.out.println("Enter your Username: ");
        username = sc.nextLine();

        System.out.println("Enter your E-Mail Address" );
        email = sc.nextLine();

        System.out.println("Enter the Master Key: ");
        masterKey = sc.nextLine();

        sc.close();

        System.out.println("Saving User...");

        Security security = new Security(firstName, lastName, username);

        masterKey = security.EncryptPassword(masterKey);

        return dbManager.SaveUser(firstName, lastName, email, username, masterKey);
    }

    public boolean VerifyUser() {
        Scanner sc = new Scanner(System.in);

        String username, password;

        System.out.println("Enter Username: ");
        username = sc.nextLine();

        System.out.println("Enter Password: ");
        password = sc.nextLine();


        boolean res = dbManager.checkUser(username);

        if(!res) {
            System.out.println("Username not valid.");
            return false;
        }

        res = dbManager.verifyUser(username, password);

        if(!res) {
            System.out.println("Invalid Credentials");
            return false;
        } else {
            System.out.println("User Logged In Successfully");
            verifiedUser = username;
            return true;
        }
    }

    public void GetLoggedInOptions() {
        System.out.println("Please Wait. Initializing...");

        if(VerifyOTP()) {
            while(true) {
                Scanner sc = new Scanner(System.in);

                int choice = 0;

                System.out.println("Enter the application you want to use: ");
                System.out.println("1. Password Manager");
                System.out.println("2. Password Generator");
                System.out.println("3. Quit");

                if(sc.hasNextInt()) {
                    choice = sc.nextInt();
                }

                switch (choice) {
                    case 1 -> UsePasswordManager();
                    case 2 -> UsePasswordGenerator();
                    case 3 -> System.exit(1);
                    default -> System.out.println("Invalid Choice");
                }
            }
        }
    }

    private void UsePasswordManager() {
        InitManager initManager = new InitManager(this.verifiedUser, this.isVerified);

        while (true) {
            Scanner sc = new Scanner(System.in);

            int choice = 0;

            System.out.println("Enter the operation you want to perform: ");
            System.out.println("1. Check Application Credentials");
            System.out.println("2. Add new Credentials");
            System.out.println("3. Update Application Credentials");
            System.out.println("4. Delete Application");
            System.out.println("5. Quit");

            if(sc.hasNextInt()) {
                choice = sc.nextInt();
            }

            switch (choice) {
                case 1 -> initManager.ListApplicationCredentials();
                case 2 -> initManager.CreateCredentials();
                case 3 -> initManager.UpdateApplicationCredentials();
                case 4 -> initManager.DeleteApplication();
                case 5 -> System.exit(1);
                default -> System.out.println("Invalid Choice");
            }
        }
    }

    private void UsePasswordGenerator() {
        InitGenerator initGenerator = new InitGenerator(this.verifiedUser);

        initGenerator.SendGeneratedPasswords();
    }
}
