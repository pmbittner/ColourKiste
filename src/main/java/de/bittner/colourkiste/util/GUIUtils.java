package de.bittner.colourkiste.util;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GUIUtils {
    public static void forAllTabsIn(final JTabbedPane tabbedPane, final Consumer<Component> action) {
        for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
            action.accept(tabbedPane.getTabComponentAt(i));
        }
    }

    public static Component findTab(final JTabbedPane tabbedPane, final Predicate<Component> condition) {
        for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
            final Component tabi = tabbedPane.getTabComponentAt(i);
            if (condition.test(tabi)) {
                return tabi;
            }
        }

        return null;
    }
}
