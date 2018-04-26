package com.LineJumper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.containers.OrderedSet;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RemoveJumpAction extends AnAction {
    public void update(AnActionEvent e) {
        e.getPresentation().setVisible(PluginState.getInstance().getState().jumps.size() != 0);
    }

    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getProject();

        JPanel panel = new JPanel();

        final JBPopup mainPopup = JBPopupFactory
                .getInstance()
                .createComponentPopupBuilder(panel, null)
                .createPopup();

        panel.setBorder(JBUI.Borders.empty(10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select jumps to remove:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);


        final OrderedSet<Integer> jumps = new OrderedSet<>(PluginState.getInstance().getState().jumps);

        final JBList list = new JBList(jumps);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(10);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 1L;

            boolean gestureStarted = false;

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(!gestureStarted){
                    if (isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index1);
                    } else {
                        super.addSelectionInterval(index0, index1);
                    }
                }
                gestureStarted = true;
            }

            @Override
            public void setValueIsAdjusting(boolean isAdjusting) {
                if (!isAdjusting) {
                    gestureStarted = false;
                }
            }
        });
        panel.add(new JBScrollPane(list));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            ArrayList<Integer> errors = new ArrayList<>();
            int[] indices = list.getSelectedIndices();
            for (int i = 0; i < indices.length; i++) {
                try {
                    Utils.removeJumpAction(jumps.get(indices[i]));
                }
                catch (InvalidJumpAmountException err) {
                    errors.add(jumps.get(i));
                }
            }

            if (errors.size() > 0) {
                StringBuilder errMsg = new StringBuilder("Error removing jumps: ");
                for (Integer error : errors) {
                    errMsg.append(error);
                    errMsg.append(" ");
                }
                JBPopupFactory
                        .getInstance()
                        .createMessage(errMsg.toString())
                        .showCenteredInCurrentWindow(project);
            }

            mainPopup.cancel();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            mainPopup.cancel();
        });

        JPanel buttonControls = new JPanel();
        buttonControls.add(cancelButton);
        buttonControls.add(removeButton);
        buttonControls.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(buttonControls);

        mainPopup.showCenteredInCurrentWindow(project);
        list.grabFocus();
    }
}

