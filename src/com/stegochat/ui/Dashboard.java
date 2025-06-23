package com.stegochat.ui;

import com.stegochat.auth.AuthService;
import com.stegochat.storage.DataStore;
import com.stegochat.model.User;
import java.util.Scanner;

/**
 * Main application dashboard/menu after authentication.
 */
public class Dashboard {
    private final AuthService auth;
    private final DataStore store;
    private final Scanner sc;

    public Dashboard(AuthService auth, DataStore store, Scanner sc) {
        this.auth = auth;
        this.store = store;
        this.sc = sc;
    }

    public void run() {
        User user = auth.getCurrentUser();
        while (true) {
            System.out.println("\n--- Dashboard for " + user.getUsername() + " ---");
            System.out.println("Your ID: " + user.getId());
            System.out.println("1) View Contacts");
            System.out.println("2) Add Contact");
            System.out.println("3) Compose Message");
            System.out.println("4) Inbox");
            System.out.println("5) Settings");
            System.out.println("6) Logout");
            System.out.print("> ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    // TODO: list contacts
                    System.out.println("[View Contacts] - feature not yet implemented.");
                    break;
                case "2":
                    // TODO: add a new contact by ID
                    System.out.println("[Add Contact] - feature not yet implemented.");
                    break;
                case "3":
                    // TODO: compose and send a message
                    System.out.println("[Compose Message] - feature not yet implemented.");
                    break;
                case "4":
                    // TODO: view inbox messages
                    System.out.println("[Inbox] - feature not yet implemented.");
                    break;
                case "5":
                    // TODO: settings (change username/password)
                    System.out.println("[Settings] - feature not yet implemented.");
                    break;
                case "6":
                    auth.logout();
                    System.out.println("Logged out successfully.\n");
                    return;
                default:
                    System.out.println("Invalid selection. Please choose 1-6.");
            }
        }
    }
}
