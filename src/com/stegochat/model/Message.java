package com.stegochat.model;

import java.util.List;

/**
 * Represents an exchanged message, including its stego envelope and optional plaintext.
 */
public class Message {
    private String messageId;
    private String senderId;
    private String recipientId;
    private String timestamp;
    private List<Step> steps;     // ordered list of steps applied
    private String coverText;     // the "innocent" text containing hidden data
    private String plaintext;     // null until decoded
    private String openedAt;      // timestamp when plaintext was first decoded

    public Message() {}

    public Message(String messageId, String senderId, String recipientId,
                   String timestamp, List<Step> steps, String coverText) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = timestamp;
        this.steps = steps;
        this.coverText = coverText;
    }

}
