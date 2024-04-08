package org.nice.pages.addressmanage;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.constants.FontSize;
import org.nice.listview.DynamicListView;
import org.nice.listview.Item;
import org.nice.models.Address;
import org.nice.navigation.Routeable;
import org.nice.services.NavigationService;
import org.nice.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class ManageAddressPage extends Routeable {
    private DynamicListView<Address> addressDynamicListView = new DynamicListView<>(
            UserService.getInstance().getCurrentUser().getAddressCollection(),
            Address::id,
            (item) -> {
                var view = new JPanel(new MigLayout("insets 12", "[][grow]"));
                view.setMaximumSize(new Dimension(360, 360));
                view.setBorder(new FlatRoundBorder());

                var nameLabel = new JLabel("Name: ");
                var phoneLabel = new JLabel("Phone: ");
                var addressLabel = new JLabel("Address");
                view.add(nameLabel);
                view.add(new JLabel(item.name()),"wrap");
                view.add(phoneLabel);
                view.add(new JLabel(item.phoneNumber()),"wrap");
                view.add(addressLabel);
                view.add(new JLabel(item.address()), "wrap");

                var buttonContainer = new JPanel(new MigLayout("align right", ""));
                var editBtn = new JButton("Edit");
                var deleteButton = new JButton("Remove");
                buttonContainer.add(editBtn, "align right");
                buttonContainer.add(deleteButton, "align right");
                view.add(buttonContainer, "align right, grow,dock south");

                deleteButton.addActionListener(v -> {
                    UserService.getInstance().getCurrentUser().removeAddress(item.id());
                    this.updateList();
                });

                Arrays.stream(view.getComponents()).filter(v -> !(v instanceof JButton)).forEach(v -> v.setFont(FontSize.x16));
                return new Item<>(view, Optional.of("grow, align center"));
            },
            new Item<>(new JLabel("Empty addresses"), Optional.of("align center")),
            new MigLayout("wrap, gapy 6", "grow")
    );

    private void updateList() {
        addressDynamicListView.update();
    }

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
