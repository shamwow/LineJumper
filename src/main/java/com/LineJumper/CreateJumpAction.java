package com.LineJumper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;

public class CreateJumpAction extends AnAction {
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getProject();
        JPanel panel = new JPanel();
        final JBPopup mainPopup = JBPopupFactory
                .getInstance()
                .createComponentPopupBuilder(panel, panel)
                .setTitle("Create Jump Action")
                .setCancelOnClickOutside(true)
                .setCancelOnOtherWindowOpen(true)
                .setFocusable(true)
                .setRequestFocus(true)
                .createPopup();

        JBTextField txtField = new JBTextField(10);

        JButton button = new JButton("Confirm");
        button.addActionListener(e -> {
            try {
                int jumpAmount = Integer.parseInt(txtField.getText());
                if (jumpAmount <= 0) {
                    throw new NumberFormatException();
                }

                try {
                    Utils.registerJumpAction(jumpAmount, true);
                    mainPopup.cancel();
                }
                catch (InvalidJumpAmountException err) {
                    JBPopupFactory
                            .getInstance()
                            .createMessage("Unexpected error")
                            .showCenteredInCurrentWindow(project);
                    mainPopup.cancel();
                }
            }
            catch(NumberFormatException err) {
                JBPopupFactory
                        .getInstance()
                        .createMessage("Please enter a valid positive integer")
                        .showCenteredInCurrentWindow(project);
            }
        });
        panel.add(new JLabel("Enter desired jump size:"));
        panel.add(txtField);
        panel.add(button);
        mainPopup.showInFocusCenter();
        txtField.grabFocus();
    }
}

