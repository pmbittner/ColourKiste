package de.bittner.colourkiste.engine.components.input;

import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.Screen;
import de.bittner.colourkiste.engine.components.graphics.FilledRectangleGraphics;
import de.bittner.colourkiste.engine.components.hitbox.Hitbox;
import de.bittner.colourkiste.math.Vec2;
import org.variantsync.functjonal.functions.TriFunction;

import java.awt.*;

public class OnClickComponent extends InputComponent {
    private final TriFunction<Entity, Integer, Vec2, Boolean> onClick;
    private final Screen screen;

    public OnClickComponent(final TriFunction<Entity, Integer, Vec2, Boolean> onClick, Screen screen) {
        this.onClick = onClick;
        this.screen = screen;
    }

    public boolean mouseClicked(int button, Vec2 pos) {
        return onClick.apply(getEntity(), button, pos);
    }

    @Override
    public boolean mouseMoved(int button, Vec2 worldPos) {
        final Hitbox hitbox = getEntity().get(Hitbox.class);
        final FilledRectangleGraphics g = getEntity().get(FilledRectangleGraphics.class);
        if (hitbox.contains(worldPos)) {
            g.setColor(Color.MAGENTA);
        } else {
            g.setColor(Color.ORANGE);
        }
        screen.refresh();
        return false;
    }
}
