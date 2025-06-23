package com.stegochat.ui;

import com.stegochat.storage.DataStore;
import com.stegochat.storage.JsonFileStore;
import com.stegochat.auth.AuthService;
import java.util.Scanner;

/**
 * Entry point and main menu handling for the console application.
 */
public class ConsoleApp {
    public static void main(String[] args) {
        String dataFile = "data/users.json";
        try {
            DataStore store = new JsonFileStore(dataFile);
            AuthService auth = new AuthService(store);
            Scanner sc = new Scanner(System.in);

            while (true) {
                if (auth.getCurrentUser() == null) {
                    System.out.println("=== Welcome to StegoChat ===");
                    System.out.println("1) Sign Up");
                    System.out.println("2) Login");
                    System.out.println("3) Exit");
                    System.out.print("> ");
                    String choice = sc.nextLine().trim();
                    switch (choice) {
                        case "1":
                            System.out.print("Username: ");
                            String newUser = sc.nextLine().trim();
                            System.out.print("Password: ");
                            String newPass = sc.nextLine().trim();
                            if (auth.register(newUser, newPass)) {
                                System.out.println("Registered and logged in as " + newUser + ".\n");
                            } else {
                                System.out.println("Username already exists. Try again.\n");
                            }
                            break;
                        case "2":
                            System.out.print("Username: ");
                            String loginUser = sc.nextLine().trim();
                            System.out.print("Password: ");
                            String loginPass = sc.nextLine().trim();
                            if (auth.login(loginUser, loginPass)) {
                                System.out.println("Logged in as " + loginUser + ".\n");
                            } else {
                                System.out.println("Invalid credentials. Try again.\n");
                            }
                            break;
                        case "3":
                            System.out.println("Exiting. Goodbye!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid selection. Please choose 1-3.\n");
                    }
                } else {
                    // User is authenticated; show dashboard
                    Dashboard dashboard = new Dashboard(auth, store, sc);
                    dashboard.run();
                }
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
