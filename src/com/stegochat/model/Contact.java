package com.stegochat.model;

/**
 * A contact entry (another user) in the user's address book.
 */
public class Contact {
    private String id;          // Contact's user ID
    private String displayName; // e.g. their username or alias

    public Contact() {}

    public Contact(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }


}