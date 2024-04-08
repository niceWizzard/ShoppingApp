package org.nice.pages.addressmanage;

import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.listview.DynamicListView;
import org.nice.listview.Item;
import org.nice.models.Address;
import org.nice.navigation.Routeable;
import org.nice.services.NavigationService;
import org.nice.services.UserService;

import javax.swing.*;
import java.util.Optional;

public class ManageAddressPage extends Routeable {
    private DynamicListView<Address> addressDynamicListView = new DynamicListView<>(
            UserService.getInstance().getCurrentUser().getAddressCollection(),
            Address::id,
            (item) -> {
                var view = new JPanel(new MigLayout("", "grow"));
                view.add(new JLabel(item.name()), "align center");
                return new Item<>(view, Optional.of("wrap, grow"));
            },
            new Item<>(new JLabel("Empty addresses"), Optional.of("align center")),
            new MigLayout("wrap", "grow")
    );
    public ManageAddressPage() {
        setLayout(new MigLayout("", "grow"));
        var header = new JPanel(new MigLayout());
        var backBtn = new JButton("Back");
        backBtn.addActionListener(v -> {
            NavigationService.getInstance().nav.navigateTo(Main.NAV_PROFILE);
        });
        header.add(backBtn, "align left");
        var title = new JLabel("Your addresses");
        header.add(title, "grow");
        add(header, "dock north");

        var scrollPane = new JScrollPane(addressDynamicListView);
        add(scrollPane, "grow");

        add(new JLabel("LSKJDF"), "dock south");
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}
