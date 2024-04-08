package org.nice.pages.addressmanage;

import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.navigation.Routeable;
import org.nice.services.NavigationService;

import javax.swing.*;

public class ManageAddressPage extends Routeable {
    public ManageAddressPage() {
        setLayout(new MigLayout());
        var header = new JPanel(new MigLayout());
        var backBtn = new JButton("Back");
        backBtn.addActionListener(v -> {
            NavigationService.getInstance().nav.navigateTo(Main.NAV_PROFILE);
        });
        header.add(backBtn, "align left");
        var title = new JLabel("Your addresses");
        header.add(title, "grow");
        add(header, "dock north");
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}
