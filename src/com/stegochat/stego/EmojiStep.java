package com.stegochat.stego;

import java.util.HashMap;
import java.util.Map;

/**
 * Emoji encoding step. Maps bytes to a sequence of emoji based on a defined map.
 */
public class EmojiStep implements StegoStep {
    private static final String[] EMOJI_MAP = new String[] {
            "😀","😁","😂","😃","😄","😅","😆","😉",
            "😊","😋","😎","😍","😘","😗","😙","😚"
    };
    private static final Map<String, Byte> REVERSE_MAP = new HashMap<>();

    static {
        for (int i = 0; i < EMOJI_MAP.length; i++) {
            REVERSE_MAP.put(EMOJI_MAP[i], (byte) i);
        }
    }

    @Override
    public String encode(String input) {
        byte[] data = input.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            // split byte into two 4-bit halves
            int high = (b >> 4) & 0xF;
            int low  = b & 0xF;
            sb.append(EMOJI_MAP[high]).append(EMOJI_MAP[low]);
        }
        return sb.toString();
    }

    @Override
    public String decode(String input) {
        byte[] result = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            byte high = REVERSE_MAP.get(input.substring(i, i+1));
            byte low  = REVERSE_MAP.get(input.substring(i+1, i+2));
            result[i/2] = (byte) ((high << 4) | low);
        }
        return new String(result);
    }
}
