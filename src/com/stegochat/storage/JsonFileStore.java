package com.stegochat.storage;

import com.stegochat.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON-based file store using GSON. Persists a list of User objects.
 */
public class JsonFileStore implements DataStore {
    private final Path filePath;
    private final Gson gson;

    public JsonFileStore(String filePath) {
        this.filePath = Paths.get(filePath);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<User> loadUsers() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        String json = Files.readString(filePath, StandardCharsets.UTF_8);
        Type listType = new TypeToken<List<User>>(){}.getType();
        List<User> users = gson.fromJson(json, listType);
        return users != null ? users : new ArrayList<>();
    }

    @Override
    public void saveUsers(List<User> users) throws IOException {
        String json = gson.toJson(users);
        Files.writeString(filePath, json, StandardCharsets.UTF_8);
    }
}
