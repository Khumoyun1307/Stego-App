package com.stegochat.storage;

import com.stegochat.model.User;
import java.io.IOException;
import java.util.List;

/**
 * Interface for loading and saving users to persistent storage.
 */
public interface DataStore {
    /**
     * Loads all users from storage. If no data exists, returns an empty list.
     */
    List<User> loadUsers() throws IOException;

    /**
     * Saves all users to storage, overwriting existing data.
     */
    void saveUsers(List<User> users) throws IOException;
}
