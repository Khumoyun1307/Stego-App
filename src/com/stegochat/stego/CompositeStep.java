package com.stegochat.stego;

import java.util.List;

/**
 * Composite step that chains multiple StegoStep implementations in a specified order.
 */
public class CompositeStep implements StegoStep {
    private final List<StegoStep> steps;

    /**
     * @param steps Ordered list of steps to apply. encode() applies in order; decode() in reverse order.
     */
    public CompositeStep(List<StegoStep> steps) {
        this.steps = steps;
    }

    @Override
    public String encode(String input) {
        String result = input;
        for (StegoStep step : steps) {
            result = step.encode(result);
        }
        return result;
    }

    @Override
    public String decode(String input) {
        String result = input;
        for (int i = steps.size() - 1; i >= 0; i--) {
            result = steps.get(i).decode(result);
        }
        return result;
    }
}
