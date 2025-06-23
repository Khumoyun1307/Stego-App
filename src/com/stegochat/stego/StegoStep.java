package com.stegochat.stego;

import java.util.List;

/**
 * Interface for a single steganography or encryption step.
 */
public interface StegoStep {
    /**
     * Encodes the input string, returning a new string containing hidden data.
     */
    String encode(String input);

    /**
     * Decodes the input string, extracting the hidden payload or reversing the step.
     */
    String decode(String input);
}
