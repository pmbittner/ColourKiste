package de.bittner.colourkiste.engine.components.input;

import de.bittner.colourkiste.engine.EntityComponent;
import de.bittner.colourkiste.math.Vec2;

public class InputComponent extends EntityComponent {

    public boolean mouseClicked(int button, Vec2 pos) {
        return false;
    }

    public boolean mouseDragStart(int button, Vec2 pos) {
        return false;
    }

    public boolean mouseDragged(int button, Vec2 pos) {
        return false;
    }

    public boolean mouseDragEnd(int button, Vec2 pos) {
        return false;
    }
}
