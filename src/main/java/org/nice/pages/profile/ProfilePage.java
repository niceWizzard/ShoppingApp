package org.nice.pages.profile;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.navigation.Routeable;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends Routeable {

    private final JPanel root;

    public ProfilePage(){
        setLayout(new MigLayout());
        setBorder(Padding.byParts(36,24));
        root = new JPanel(new MigLayout("", "grow"));
        root.setBorder(
                BorderFactory.createCompoundBorder(
                        new FlatRoundBorder(),
                        Padding.byParts(24,18)
                )
        );
        root.setPreferredSize(new Dimension(1080,1080));
        add(root);
        init();
    }

    private void init() {
        var northContainer = new JPanel(new MigLayout("", "grow"));
        var username = new JLabel("Username");
        username.setFont(FontSize.x24b);
        northContainer.add(username, "align left, wrap, grow");
        northContainer.setBorder(
                BorderFactory.createMatteBorder(0,0,1,0,
                        UIManager.getColor("Component.borderColor"))
        );


        northContainer.add(new AddressPanel(), "grow,wrap");


        var addressesBtn = new JButton("Manage Addresses");
        northContainer.add(addressesBtn, "al right");

        root.add(northContainer, "dock north, grow, wrap");

        var checkoutHistory = new CheckoutHistory();
        root.add(checkoutHistory, "grow, gapy 36");



    }

    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}

class AddressPanel extends  JPanel{
    public AddressPanel() {
        setLayout(new MigLayout("wrap, al left top", "grow"));

        setBorder(
                BorderFactory.createCompoundBorder(
                        new FlatRoundBorder(),
                        Padding.byParts(12,6)
                )
        );

        var name = new JLabel("Richard Manansala");
        var address = new JLabel("Your address.sdlakfjalskdjfwe asldfkjasdf");
        var phoneNumber = new JLabel("0292993 933");

        add(name);
        add(phoneNumber);
        add(address);
    }
}