package com.stegochat.model;

/**
 * Represents one transformation step in a message (e.g. Base64, ZeroWidth, Crypto).
 */
public class Step {
    private StepType type;
    private String password;  // only used if type == CRYPTO

    public Step() {}

    public Step(StepType type, String password) {
        this.type = type;
        this.password = password;
    }

    // Getters and setters omitted for brevity
}