package org.nice.pages;

import org.nice.navigation.Routeable;

import javax.swing.*;

public class HomePage extends Routeable {
    public HomePage() {
        add(new JLabel("HomePage"));
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}
