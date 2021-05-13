package PasswordManager;

import java.util.ArrayList;
import java.util.Scanner;

public class InitManager {
    private final String CurrentUser;
    private final boolean isVerified;

    DBManager dbManager;

    public InitManager(String CurrentUser, boolean isVerified) {
        dbManager = new DBManager();
        this.CurrentUser = CurrentUser;
        this.isVerified = isVerified;
    }

    public void ListApplicationCredentials() {
        if(isVerified) {
            if(dbManager.CheckUserApplications(this.CurrentUser)) {
                Scanner sc = new Scanner(System.in);
                ArrayList<String> allApps = dbManager.ListApplicationCreds(CurrentUser);
                int choice;

                System.out.println("Select the Application you want to see credentials: ");

                for(int i = 0; i < allApps.size(); i++) {
                    System.out.println((i + 1) + ":\t" + allApps.get(i));
                }

                System.out.println("Enter Choice: ");
                choice = sc.nextInt();

                Credentials getCredentials = new Credentials();

                getCredentials.Get(CurrentUser, allApps.get(choice - 1));
            } else {
                System.out.println("User Does Not Have Any Credentials Stored.");
            }
        } else {
            System.out.println("OTP not verified");
        }
    }

    public void CreateCredentials() {
        if(isVerified) {
            Credentials newCredentials = new Credentials();

            if(newCredentials.Add(CurrentUser)) {
                System.out.println("Credentials Saved Successfully.");
            }
        } else {
            System.out.println("Your OTP is not verified.");
        }
    }

    public void UpdateApplicationCredentials() {
        if(isVerified) {
            Credentials credentials = new Credentials();

            if(credentials.Update(CurrentUser)) {
                System.out.println("Credentials Updated Successfully");
            }
        } else {
            System.out.println("Your OTP is not verified.");
        }
    }

    public void DeleteApplication() {
        if(isVerified) {
            Credentials credentials = new Credentials();

            if(credentials.Delete(CurrentUser)) {
                System.out.println("Application Deleted Successfully");
            }
        } else {
            System.out.println("Your OTP is not verified");
        }
    }
}
