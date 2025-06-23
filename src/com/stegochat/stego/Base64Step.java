package com.stegochat.stego;

import java.util.Base64;

/**
 * Base64 encoding step.
 */
public class Base64Step implements StegoStep {
    @Override
    public String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    @Override
    public String decode(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
