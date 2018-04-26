package com.LineJumper;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

public class PluginRegistration implements ApplicationComponent {
    // Returns the component name (any unique string value).
    @NotNull
    public String getComponentName() {
        return "LineJumper";
    }


    // If you register the MyPluginRegistration class in the <application-components> section of
    // the plugin.xml file, this method is called on IDEA start-up.
    public void initComponent() {
        PluginState.getInstance().getState().jumps.forEach(jumpAmount -> {
            try {
                Utils.registerJumpAction(jumpAmount, false);
            } catch (InvalidJumpAmountException e) {
                throw new RuntimeException(
                        "Unable to load saved jumps. Check configuration settings, all jump amount must be positive integers and unique.");
            }
        });
    }

    // Disposes system resources.
    public void disposeComponent() {
    }
}