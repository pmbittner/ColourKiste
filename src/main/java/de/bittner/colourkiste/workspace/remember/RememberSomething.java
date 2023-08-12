package de.bittner.colourkiste.workspace.remember;

import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.commands.IdCommand;

import java.util.Objects;

public class RememberSomething extends IdCommand<Texture> {
    public enum Policy {
        REMEMBER_ALL,
        REMEMBER_LAST
    }

    private final Policy policy;

    public RememberSomething(Policy policy) {
        this.policy = policy;
    }

    public Policy policy() {
        return policy;
    }
}
