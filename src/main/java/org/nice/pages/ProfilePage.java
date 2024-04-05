package org.nice.pages;

import org.nice.navigation.Routeable;

import javax.swing.*;

public class ProfilePage extends Routeable {

    public ProfilePage(){
        add(new JLabel("Profile"));
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}
