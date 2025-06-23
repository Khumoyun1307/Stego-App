package com.stegochat.stego;

/**
 * Zero-width character encoding step. Hides data by mapping bits to zero-width spaces and zero-width non-joiners.
 */
public class ZeroWidthStep implements StegoStep {
    private static final char ZW_SPACE = '\u200B';       // bit 0
    private static final char ZW_NON_JOINER = '\u200C';  // bit 1

    @Override
    public String encode(String input) {
        StringBuilder binary = new StringBuilder();
        for (byte b : input.getBytes()) {
            for (int i = 7; i >= 0; i--) {
                binary.append(((b >> i) & 1) == 1 ? ZW_NON_JOINER : ZW_SPACE);
            }
        }
        return binary.toString();
    }

    @Override
    public String decode(String input) {
        byte[] bytes = new byte[input.length() / 8];
        for (int byteIdx = 0; byteIdx < bytes.length; byteIdx++) {
            byte b = 0;
            for (int bit = 0; bit < 8; bit++) {
                char c = input.charAt(byteIdx * 8 + bit);
                b <<= 1;
                if (c == ZW_NON_JOINER) {
                    b |= 1;
                }
            }
            bytes[byteIdx] = b;
        }
        return new String(bytes);
    }
}

