package com.stegochat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User {
    private String id;                  // UUID string
    private String username;
    private String hashedPassword;
    private List<Contact> contacts = new ArrayList<>();
    private List<Message> inbox    = new ArrayList<>();
    private List<Message> sent     = new ArrayList<>();

    public User() {}

    public User(String id, String username, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public String getId() {
        return id;
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public List<Message> getSent() {
        return sent;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getUsername() {
        return username;
    }
}
