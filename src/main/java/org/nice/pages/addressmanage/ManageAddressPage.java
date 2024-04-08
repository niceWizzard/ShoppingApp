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
import org.nice.services.AddressService;
import org.nice.services.NavigationService;
import org.nice.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
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
            AddressService.getInstance().getAddressCollection(),
            Address::id,
            (item) -> {
                var view = new AddressPanel(item);
                return new Item<>(view, Optional.of("grow, align center"));
            },
            new Item<>(new JLabel("Empty addresses"), Optional.of("align center")),
            new MigLayout("wrap, gapy 6, height 480::", "grow")
    );

    public static class AddressPanel extends JPanel {

        private final Disposable subscription;
        private final Disposable subscription2;
        private Address item;

        @Override
        public void removeNotify() {
            super.removeNotify();
            subscription.dispose();
            subscription2.dispose();
        }

        public AddressPanel(Address givenAddress) {
            this.item = givenAddress;
            setLayout(new MigLayout("insets 12", "[][grow]"));
            setMaximumSize(new Dimension(360, 360));
            setBorder(new FlatRoundBorder());

            var nameLabel = new JLabel("Name: ");
            var phoneLabel = new JLabel("Phone: ");
            var addressLabel = new JLabel("Address");
            var nameField = new JLabel(givenAddress.name());
            var phoneField = new JLabel(givenAddress.phoneNumber());
            var addressField = new JLabel(givenAddress.address());
            add(nameLabel);
            add(nameField,"wrap");
            add(phoneLabel);
            add(phoneField,"wrap");
            add(addressLabel);
            add(addressField, "wrap");

            this.subscription = AddressService.getInstance().listenForChanges(givenAddress.id()).subscribe(v -> {
                nameField.setText(v.name());
                phoneField.setText(v.phoneNumber());
                addressField.setText(v.address());

                this.item = v;
            });



            var buttonContainer = new JPanel(new MigLayout("align right", ""));
            var editBtn = new JButton("Edit");
            var deleteButton = new JButton("Remove");
            var assignBtn = new JButton("Set main");
            buttonContainer.add(assignBtn, "align left");
            buttonContainer.add(editBtn, "align right");
            buttonContainer.add(deleteButton, "align right");
            add(buttonContainer, "align right, grow,dock south");

            assignBtn.addActionListener(v -> {
                AddressService.getInstance().setMainAddress(this.item);
            });

            editBtn.addActionListener(v -> {
                new CreateAddressModal(Optional.of(this.item));
            });

            deleteButton.addActionListener(v -> {
                AddressService.getInstance().removeAddress(givenAddress.id());
            });

            this.subscription2 = AddressService.getInstance().getMainAddressObservable().subscribe(v -> {
                var isMainAddress = !v.id().equals(this.item.id());
                assignBtn.setEnabled(isMainAddress);
                deleteButton.setEnabled(isMainAddress);
            });

            Arrays.stream(getComponents()).filter(v -> !(v instanceof JButton)).forEach(v -> v.setFont(FontSize.x16));
        }
    }

    public ManageAddressPage() {
        setLayout(new MigLayout("", "grow", "grow"));

        this.subscription = AddressService.getInstance().getAddresses().subscribe(v -> {
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
