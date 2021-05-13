package PasswordGenerator;

import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class InitGenerator {

    DBManager dbManager;

    private final String username;

    private String[] passwords;

    private Hashtable<String, String> userPII = new Hashtable<>();
    private int passwordLength = 8;
    private int noOfPasswords = 0;
    private String specialCharacters;

    public InitGenerator(String username) {
        dbManager = new DBManager();

        this.username = username;
    }

    private void GetUsersPII() {
        Scanner sc = new Scanner(System.in);

        String FirstName, LastName, BirthDay, BirthMonth, BirthYear, EMail, HouseNumber, City, State, Country,
                PhoneNumber, CollegeName, CarNumber;

        System.out.println("Enter Your First Name: ");
        FirstName = sc.nextLine();

        System.out.println("Enter Your Last Name: ");
        LastName = sc.nextLine();

        System.out.println("Enter Your Date of Birth Details:");
        System.out.println("Enter Your Birth Date: ");
        BirthDay = sc.nextLine();
        System.out.println("Enter Your Birth Month: ");
        BirthMonth = sc.nextLine();
        System.out.println("Enter Your Birth Year:  ");
        BirthYear = sc.nextLine();

        System.out.println("Enter Your E-Mail: ");
        EMail = sc.nextLine();

        System.out.println("Enter Your House Number: ");
        HouseNumber = sc.nextLine();

        System.out.println("Enter Your City: ");
        City = sc.nextLine();

        System.out.println("Enter Your State: ");
        State = sc.nextLine();

        System.out.println("Enter Your Country: ");
        Country = sc.nextLine();

        System.out.println("Enter Your Phone Number: ");
        PhoneNumber = sc.nextLine();

        System.out.println("Enter Your College Name: ");
        CollegeName = sc.nextLine();

        System.out.println("Enter Your Car Number");
        CarNumber = sc.nextLine();

        // Store the data in dictionary format
        userPII.put("FirstName", FirstName);
        userPII.put("LastName", LastName);
        userPII.put("BirthDay", BirthDay);
        userPII.put("BirthMonth", BirthMonth);
        userPII.put("BirthYear", BirthYear);
        userPII.put("EMail", EMail);
        userPII.put("HouseNumber", HouseNumber);
        userPII.put("City", City);
        userPII.put("State", State);
        userPII.put("Country", Country);
        userPII.put("PhoneNumber", PhoneNumber);
        userPII.put("CollegeName", CollegeName);
        userPII.put("CarNumber", CarNumber);

        System.out.println("Would like to store us this data for future reference? (Y/N)");
        String ch = sc.nextLine();

        if(ch.equals("Y")) {
            if(saveUserPII()) {
                System.out.println("Data Saved Successfully.");
            }
        }
    }

    private void GetUserPIIFromDB() {
        userPII = dbManager.GetUserPII(this.username);
    }

    private boolean saveUserPII() {
        return dbManager.SaveUserPII(userPII, this.username);
    }

    private void GetPasswordLength() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the required password length (Min Length 8): ");
        this.passwordLength = sc.nextInt();
    }

    private void GetNumberOfPasswords() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of passwords required: ");
        this.noOfPasswords = sc.nextInt();

        passwords = new String[this.noOfPasswords];
    }

    private void GetSpecialChars() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Allowed Special Characters (Without Space/Next Line): ");
        specialCharacters = sc.nextLine();

        System.out.println("\nThank you for providing us with allowed Special Characters.");

    }

    private char GetRandomSpecialChar() {
        char specialCharacter;

        int index = new Random().nextInt(this.specialCharacters.length());

        if(index > 0) {
            specialCharacter = specialCharacters.charAt(index - 1);
        } else {
            specialCharacter = specialCharacters.charAt(index);
        }

        return specialCharacter;
    }

    private char GetRandomAlphabet() {
        String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        return alphabets.charAt(new Random().nextInt(alphabets.length()));
    }

    private int GetRandomTwoDigitNumber() {
        int num = new Random().nextInt(100);

        if(Integer.toString(num).length() == 2) {
            return num;
        } else {
            return 99;
        }
    }

    private String[] PasswordLength_8() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear");
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar();
        Passwords[5] = this.userPII.get("LastName".substring(0,4)) + this.userPII.get("BirthYear");
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2);
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber");
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2);
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber");
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4);
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4);

        return Passwords;
    }

    private String[] PasswordLength_9() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() + GetRandomAlphabet() +
                GetRandomSpecialChar() + this.userPII.get("BirthDay");
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() + GetRandomAlphabet() +
                GetRandomSpecialChar() + this.userPII.get("BirthMonth");
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomAlphabet();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() + GetRandomAlphabet() +
                GetRandomSpecialChar() + this.userPII.get("BirthDay");
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() + GetRandomAlphabet() +
                GetRandomSpecialChar() + this.userPII.get("BirthMonth");
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomAlphabet();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2)+ GetRandomAlphabet();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomAlphabet();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + GetRandomAlphabet();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomAlphabet();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomAlphabet();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomAlphabet();

        return Passwords;
    }

    private String[] PasswordLength_10() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthDay");
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthMonth");
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomTwoDigitNumber();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthDay");
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthMonth");
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomTwoDigitNumber();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2)+ GetRandomTwoDigitNumber();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomTwoDigitNumber();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + GetRandomTwoDigitNumber();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomTwoDigitNumber();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomTwoDigitNumber();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomTwoDigitNumber();

        return Passwords;
    }

    private String[] PasswordLength_11() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthDay")
                + GetRandomSpecialChar();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthMonth")
                + GetRandomSpecialChar();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomSpecialChar()
                + GetRandomTwoDigitNumber();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthDay")
                + GetRandomSpecialChar();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                GetRandomTwoDigitNumber() + GetRandomSpecialChar() + this.userPII.get("BirthMonth")
                + GetRandomSpecialChar();
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear") + GetRandomSpecialChar()
                + GetRandomTwoDigitNumber();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();

        return Passwords;
    }

    private String[] PasswordLength_12() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2);
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2);

        return Passwords;
    }

    private String[] PasswordLength_13() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar();

        return Passwords;
    }

    private String[] PasswordLength_14() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet();

        return Passwords;
    }

    private String[] PasswordLength_15() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomTwoDigitNumber();

        return Passwords;
    }

    private String[] PasswordLength_16() {
        String[] Passwords = new String[12];

        Passwords[0] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[1] = this.userPII.get("FirstName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[2] = this.userPII.get("FirstName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();
        Passwords[3] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthDay") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[4] = this.userPII.get("LastName").substring(0, 4) + GetRandomSpecialChar() +
                this.userPII.get("BirthMonth") + GetRandomSpecialChar() + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[5] = this.userPII.get("LastName").substring(0,4) + this.userPII.get("BirthYear")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();
        Passwords[6] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[7] = this.userPII.get("FirstName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();
        Passwords[8] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("HouseNumber").substring(0, 2) +
                this.userPII.get("Country").substring(0, 2) + this.userPII.get("City").substring(0,2)
                + this.userPII.get("State").substring(0,2) + GetRandomSpecialChar() + GetRandomAlphabet()
                + GetRandomTwoDigitNumber();
        Passwords[9] = this.userPII.get("LastName").substring(0, 4) + this.userPII.get("CarNumber")
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();
        Passwords[10] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();
        Passwords[11] = this.userPII.get("FirstName").substring(0, 4) +
                this.userPII.get("PhoneNumber").substring(this.userPII.get("PhoneNumber").length() - 4)
                + this.userPII.get("City").substring(0,2) + this.userPII.get("State").substring(0,2)
                + GetRandomSpecialChar() + GetRandomAlphabet() + GetRandomTwoDigitNumber();

        return Passwords;
    }

    private void GeneratePasswords() {
        switch (this.passwordLength) {
            case 8 -> this.passwords = PasswordLength_8();
            case 9 -> this.passwords = PasswordLength_9();
            case 10 -> this.passwords = PasswordLength_10();
            case 11 -> this.passwords = PasswordLength_11();
            case 12 -> this.passwords = PasswordLength_12();
            case 13 -> this.passwords = PasswordLength_13();
            case 14 -> this.passwords = PasswordLength_14();
            case 15 -> this.passwords = PasswordLength_15();
            case 16 -> this.passwords = PasswordLength_16();
            default -> System.out.println("Invalid Password Length");
        }
    }

    public void SendGeneratedPasswords() {
        if(!dbManager.CheckUserInDB(this.username)) {
            GetUsersPII();
        } else {
            System.out.println("Fetching User Data from DB");
            GetUserPIIFromDB();
        }
        GetPasswordLength();
        GetNumberOfPasswords();
        GetSpecialChars();
        GeneratePasswords();

        for(int i = 0; i < this.noOfPasswords; i++) {
            System.out.println((i + 1) + ". " + passwords[i]);
        }
    }
}
