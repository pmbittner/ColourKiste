package de.bittner.colourkiste;

import org.tinylog.Logger;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyMap {
    public static final String CSV_DELIMITER_DEFAULT = "=";
    public static final String NOOP_ACTION = "NOOP";

    private final LinkedHashMap<KeyStroke, String> map;

    public KeyMap() {
        this(new LinkedHashMap<>());
    }

    public KeyMap(final LinkedHashMap<KeyStroke, String> map) {
        this.map = map;
    }

    public static KeyMap fromFile(final Path filepath, final String delimiter) {
        try (final Stream<String> lines = Files.lines(filepath)) {
            return new KeyMap(lines
                    .map(line -> {
                        Logger.info("Parse line " + line);
                        return line.split(delimiter);
                    })
                    .collect(Collectors.toMap(
                            cols -> {
                                return KeyStroke.getKeyStroke(cols[0].trim());
                            },
                            cols -> cols[1].trim(),
                            (l, r) -> l,
                            LinkedHashMap::new
                    )));
        } catch (IOException e) {
            Logger.error("Failed importing keybindings from " + filepath + "!");
            return null;
        }
    }

    public void bindKey(final KeyStroke k, final String action, boolean overwrite) {
        if (!overwrite && map.containsKey(k)) return;
        map.put(k, action);
    }

    public void bindAll(final KeyMap other, boolean overwrite) {
        for (final Map.Entry<KeyStroke, String> binding : other.getAllBindings().entrySet()) {
            this.bindKey(binding.getKey(), binding.getValue(), overwrite);
        }
    }

    public String getAction(final KeyStroke k) {
        return map.getOrDefault(k, NOOP_ACTION);
    }

    public LinkedHashMap<KeyStroke, String> getAllBindings() {
        return map;
    }

    public KeyStroke getFirstKeybindingForAction(final String action) {
        for (final Map.Entry<KeyStroke, String> binding : map.entrySet()) {
            if (binding.getValue().equals(action)) {
                return binding.getKey();
            }
        }

        return null;
    }
}
