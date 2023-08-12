package de.bittner.colourkiste;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {
    private Map<String, KeyStroke> bindings = new HashMap<>();

    public void setBinding(final String command, final KeyStroke keyStroke, boolean overwrite) {
        if (!overwrite && bindings.containsKey(command)) {
            return;
        }

        bindings.put(command, keyStroke);
    }
}
