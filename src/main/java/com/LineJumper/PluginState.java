package com.LineJumper;

import com.intellij.openapi.components.*;
import com.intellij.util.containers.OrderedSet;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name="LineJumperSettings",
        storages=@Storage(id="LineJumper", file=StoragePathMacros.APP_CONFIG+"/linejumper.xml")
)
public class PluginState  implements PersistentStateComponent<PluginState> {
    static @NotNull PluginState getInstance() {
        PluginState state = ServiceManager.getService(PluginState.class);
        if (state == null) {
            throw new RuntimeException("State service was null");
        }
        return state;
    }

    // Needs to be public.
    public OrderedSet<Integer> jumps = new OrderedSet<>();

    public @NotNull PluginState getState() {
        return this;
    }

    public void loadState(PluginState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    void addToState(int jumpAmount) throws InvalidJumpAmountException {
        if (jumps.contains(jumpAmount)) {
            throw new InvalidJumpAmountException();
        }
        jumps.add(jumpAmount);
    }

    void removeFromState(int jumpAmount) throws InvalidJumpAmountException {
        if (!jumps.contains(jumpAmount)) {
            throw new InvalidJumpAmountException();
        }
        jumps.remove(new Integer(jumpAmount));
    }
}