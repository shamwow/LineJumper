package com.LineJumper;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

abstract class Utils {
    static void registerJumpAction(int jumpAmount, boolean addToState) throws InvalidJumpAmountException {
        ActionManager am = ActionManager.getInstance();

        JumpAction upAction = new JumpAction(jumpAmount);
        am.registerAction("JumpUp" + jumpAmount, upAction);

        JumpAction downAction = new JumpAction(-jumpAmount);
        am.registerAction("JumpDown" + jumpAmount, downAction);

        DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("LineJumper");

        windowM.add(upAction);
        windowM.add(downAction);

        if (addToState) {
            PluginState.getInstance().addToState(jumpAmount);
        }
    }

    static void removeJumpAction(int jumpAmount) throws InvalidJumpAmountException {
        ActionManager am = ActionManager.getInstance();

        String upKey = "JumpUp" + jumpAmount;
        String downKey = "JumpDown" + jumpAmount;
        AnAction upAction = am.getAction(upKey);
        AnAction downAction = am.getAction(downKey);
        if (upAction == null || downAction == null) {
            throw new InvalidJumpAmountException();
        }

        DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("LineJumper");

        windowM.remove(am.getAction(upKey));
        windowM.remove(am.getAction(downKey));

        am.unregisterAction(upKey);
        am.unregisterAction(downKey);

        PluginState.getInstance().removeFromState(jumpAmount);
    }
}
