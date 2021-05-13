package Main;

import Core.UserManagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Password Manager");
        System.out.println("Select any one option:");
        System.out.println("1. New User Registration");
        System.out.println("2. Login as User");

        choice = sc.nextInt();

        UserManagement userManagement = new UserManagement();

        switch (choice) {
            case 1: if(userManagement.CreateNewUser()) {
                        System.out.println("User Created Successfully");
                    }
                    break;
            case 2: if(userManagement.VerifyUser()) {
                        userManagement.GetLoggedInOptions();
                    }
                    break;
            default: System.out.println("Invalid Choice");
        }
    }
}
