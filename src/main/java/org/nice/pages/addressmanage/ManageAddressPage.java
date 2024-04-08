package org.nice.pages.addressmanage;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import io.reactivex.rxjava3.disposables.Disposable;
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
    private final Disposable subscription;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

    public DynamicListView<Address> addressDynamicListView = new DynamicListView<>(
            UserService.getInstance().getCurrentUser().getAddressCollection(),
            Address::id,
            (item) -> {
                var view = new AddressPanel(item);
                return new Item<>(view, Optional.of("grow, align center"));
            },
            new Item<>(new JLabel("Empty addresses"), Optional.of("align center")),
            new MigLayout("wrap, gapy 6", "grow")
    );

    public class AddressPanel extends JPanel {

        private final Disposable subscription;

        @Override
        public void removeNotify() {
            super.removeNotify();
            subscription.dispose();
        }

        public AddressPanel(Address item) {
            setLayout(new MigLayout("insets 12", "[][grow]"));
            setMaximumSize(new Dimension(360, 360));
            setBorder(new FlatRoundBorder());

            var nameLabel = new JLabel("Name: ");
            var phoneLabel = new JLabel("Phone: ");
            var addressLabel = new JLabel("Address");
            var nameField = new JLabel(item.name());
            var phoneField = new JLabel(item.phoneNumber());
            var addressField = new JLabel(item.address());
            add(nameLabel);
            add(nameField,"wrap");
            add(phoneLabel);
            add(phoneField,"wrap");
            add(addressLabel);
            add(addressField, "wrap");

            this.subscription = UserService.getInstance().getCurrentUser().getOnAddressUpdated().subscribe(v -> {
                if(v.id().equals(item.id())) {
                    nameField.setText(v.name());
                    phoneField.setText(v.phoneNumber());
                    addressField.setText(v.address());
                }
            });

            var buttonContainer = new JPanel(new MigLayout("align right", ""));
            var editBtn = new JButton("Edit");
            var deleteButton = new JButton("Remove");
            buttonContainer.add(editBtn, "align right");
            buttonContainer.add(deleteButton, "align right");
            add(buttonContainer, "align right, grow,dock south");

            editBtn.addActionListener(v -> {
                new CreateAddressModal(Optional.of(item));
            });

            deleteButton.addActionListener(v -> {
                UserService.getInstance().getCurrentUser().removeAddress(item.id());
            });

            Arrays.stream(getComponents()).filter(v -> !(v instanceof JButton)).forEach(v -> v.setFont(FontSize.x16));
        }
    }

    public ManageAddressPage() {
        setLayout(new MigLayout("", "grow"));

        this.subscription = UserService.getInstance().getCurrentUser().getAddresses().subscribe(v -> {
            addressDynamicListView.update();
        });

        var header = new JPanel(new MigLayout("", "grow"));
        var backBtn = new JButton("Back");
        backBtn.addActionListener(v -> {
            NavigationService.getInstance().nav.navigateTo(Main.NAV_PROFILE);
        });

        header.add(backBtn, "align left");
        var title = new JLabel("Your addresses");
        header.add(title, "grow");

        var addBtn = new JButton("Add new address");
        header.add(addBtn, "align right");

        addBtn.addActionListener(e -> {
            new CreateAddressModal(Optional.empty());
        });

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
