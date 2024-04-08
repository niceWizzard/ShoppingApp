package org.nice.pages.addressmanage;

import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.components.MainButton;
import org.nice.models.Address;
import org.nice.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class CreateAddressModal extends JDialog {
    public CreateAddressModal(Optional<Address> address) {
        setMinimumSize(new Dimension(240,240));
        setTitle("Update address");
        init(address);

        setLocationRelativeTo(Main.frame);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void init(Optional<Address> address) {
        setLayout(new MigLayout("wrap, insets 24", "grow"));
        var nameLabel = new JLabel("Name: ");
        var phoneLabel = new JLabel("Phone: ");
        var addressLabel = new JLabel("Address: ");

        var nameField = new JTextField();
        var phoneField =new JTextField();
        var addressField = new JTextField();

        address.ifPresent(v -> {
            nameField.setText(v.name());
            phoneField.setText(v.phoneNumber());
            addressField.setText(v.address());
        });

        var form = new JPanel(new MigLayout("", "[][grow]"));

        form.add(nameLabel);
        form.add(nameField, "wrap, grow");
        form.add(phoneLabel);
        form.add(phoneField, "wrap, grow");
        form.add(addressLabel);
        form.add(addressField, "wrap, grow");
        add(form, "grow");
        var btn = new MainButton("Save");
        add(btn, "growx");
        btn.addActionListener(v -> {
            String phoneText = phoneField.getText();
            String nameText = nameField.getText();
            String addressText = addressField.getText();
            if(
                phoneText.isBlank() ||
                nameText.isBlank() || addressText.isBlank()) {
                JOptionPane.showMessageDialog(Main.frame, "Please fill in all fields.");
                return;
            }
            var id = address.isPresent() ? address.get().id() : UUID.randomUUID();
            var add = new Address(
                    nameText,
                    phoneText,
                    addressText,
                    id.toString()
            );
            var service = UserService.getInstance().getCurrentUser();
            if (address.isPresent()) {
                service.updateAddress(add);
            } else {
                service.addAddress(add);
            }

            dispose();

        });



    }
}
