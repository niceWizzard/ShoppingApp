package org.nice.pages.profile;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import io.reactivex.rxjava3.disposables.Disposable;
import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.navigation.Routeable;
import org.nice.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class ProfilePage extends Routeable {

    private final JPanel root;
    private Disposable subscription;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

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
        var northContainer = new JPanel(new MigLayout("", "grow", ""));
        var usernameContainer = new JPanel(new MigLayout("", "[grow][shrink]"));
        var username = new JLabel("Username");
        username.setFont(FontSize.x24b);
        subscription = UserService.getInstance().getCurrentUser().getUsername().subscribe(username::setText);

        var editUsernameBtn = getUsernameBtn();
        usernameContainer.add(username, "align left,  grow");
        usernameContainer.add(editUsernameBtn, "align right, wrap, shrink");

        northContainer.add(usernameContainer, "grow,wrap");
        northContainer.add(new AddressPanel(), "grow, wrap");

        var addressesBtn = new JButton("Manage Addresses");
        northContainer.add(addressesBtn, "al right");

        root.add(northContainer, "dock north, grow, wrap");
        northContainer.setBorder(
                BorderFactory.createMatteBorder(0,0,1,0,
                        UIManager.getColor("Component.borderColor"))
        );

        var checkoutHistory = new CheckoutHistory();
        root.add(checkoutHistory, "grow, gapy 36");



    }

    private  JButton getUsernameBtn() {
        var editUsernameBtn = new JButton("Edit");
        editUsernameBtn.addActionListener(v -> {
            var dialog = new JDialog(Main.frame, "Edit username", Dialog.ModalityType.APPLICATION_MODAL);
            var root = new JPanel(new MigLayout("wrap, insets 24", "grow"));
            dialog.getContentPane().add(root);
            var label = new JLabel("New username: ");
            var input = new JTextField();
            var submitBtn = new JButton("Update");
            root.add(label, "grow");
            root.add(input, "grow");
            root.add(submitBtn, "grow");

            submitBtn.addActionListener(e -> {
                var text = input.getText();
                var matcher = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*[a-zA-Z0-9]$");
                if(!matcher.matcher(text).find()) {
                    JOptionPane.showMessageDialog(Main.frame, "Username can only contain letters, digits, _ (Must start with letter and cannot end in _)");
                    return;
                }
                UserService.getInstance().setUsername(text);
                dialog.setVisible(false);
                dialog.dispose();
            });

            dialog.setSize(240,180);
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(Main.frame);
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        });
        return editUsernameBtn;
    }

    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}

class AddressPanel extends  JPanel{
    private final Disposable subscription;
    private final JLabel phoneNumber;
    private final JLabel address;
    private final JLabel name;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

    public AddressPanel() {

        setLayout(new MigLayout("wrap, al left top", "grow"));
        setBorder(
                BorderFactory.createCompoundBorder(
                        new FlatRoundBorder(),
                        Padding.byParts(12,6)
                )
        );

        name = new JLabel("Default name");
        address = new JLabel("Your address.sdlakfjalskdjfwe asldfkjasdf");
        phoneNumber = new JLabel("0292993 933");

        add(name);
        add(phoneNumber);
        add(address);

        subscription = UserService.getInstance().getMainAddressObservable().subscribe(
                v -> {
                    name.setText(v.name());
                    phoneNumber.setText(v.phoneNumber());
                    address.setText(v.address());
                }
        );
    }
}