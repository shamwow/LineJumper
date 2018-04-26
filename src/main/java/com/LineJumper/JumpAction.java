package com.LineJumper;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;

public class JumpAction extends AnAction {
    private int jumpAmount;

    JumpAction(int jumpAmount) throws InvalidJumpAmountException {
        super();

        if (jumpAmount == 0) {
            throw new InvalidJumpAmountException();
        }
        this.jumpAmount = jumpAmount;
        Presentation presentation = this.getTemplatePresentation();
        presentation.setText("Jump " + (jumpAmount < 0 ? "Up " : "Down ") + Math.abs(jumpAmount));
        presentation.setDescription("Move text caret the specified number of lines.");
    }

    public void actionPerformed(AnActionEvent event) {
        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        editor.getCaretModel().getAllCarets().forEach(c -> {
            c.moveCaretRelatively(0, jumpAmount, event.getInputEvent().isShiftDown(), true);
        });
    }
}