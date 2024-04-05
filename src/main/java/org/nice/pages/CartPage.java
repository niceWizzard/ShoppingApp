package org.nice.pages;

import org.nice.navigation.Routeable;

import javax.swing.*;

public class CartPage extends Routeable {
    public CartPage() {
        add(new JLabel("Cart Page"));
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {
    }
}
