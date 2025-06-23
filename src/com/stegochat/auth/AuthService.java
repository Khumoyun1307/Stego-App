package com.stegochat.auth;

import com.stegochat.model.User;
import com.stegochat.storage.DataStore;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service for user registration and authentication.
 */
public class AuthService {
    private final DataStore store;
    private final List<User> users;
    private User currentUser;

    public AuthService(DataStore store) throws IOException {
        this.store = store;
        this.users = store.loadUsers();
    }

    /**
     * Registers a new user. Returns true if successful; false if username exists.
     */
    public boolean register(String username, String password) throws IOException {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        String id = UUID.randomUUID().toString();
        String hashed = PasswordHasher.hash(password);
        User newUser = new User(id, username, hashed);
        users.add(newUser);
        store.saveUsers(users);
        currentUser = newUser;
        return true;
    }

    /**
     * Logs in with username/password. Returns true if credentials match.
     */
    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    PasswordHasher.verify(password, u.getHashedPassword())) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the currently authenticated user, or null.
     */
    public User getCurrentUser() {
        return currentUser;
    }
}