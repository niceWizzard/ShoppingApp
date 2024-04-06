package org.nice.components;

import javax.swing.*;

public class MainButton extends JButton {
    public MainButton(String text) {
        setText(text);
        setBackground(UIManager.getColor("Button.default.background"));
        setForeground(UIManager.getColor("Button.default.foreground"));
    }
}
