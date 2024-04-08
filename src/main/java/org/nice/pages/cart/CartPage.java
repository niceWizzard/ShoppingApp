package org.nice.pages.cart;

import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import io.reactivex.rxjava3.disposables.Disposable;
import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.navigation.Routeable;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;

public class CartPage extends Routeable {
    private final Disposable subscription;
    private JLabel cartTotal;
    private JButton checkoutButton;
    private JButton clearAllBtn;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

    public CartPage() {
        init();
        subscription = CartService.getInstance().getCartObservable().subscribe(cartList  -> {
            var values = cartList.values();
            checkoutButton.setEnabled(!values.isEmpty());
            clearAllBtn.setVisible(!values.isEmpty());
            var totalPrice = 0f;
            for(var p : values) {
                totalPrice += p.getTotalPrice();
            }
            cartTotal.setText("Total: P" +totalPrice);

        });
    }

    private void init() {
        setBorder(Padding.byParts(24, 12));
        setLayout(new MigLayout("", "grow"));

        var upperContainer = new JPanel(new MigLayout("", "[grow]", "[grow]"));

        var cartTitle = new JLabel("Your Cart");
        cartTitle.setFont(FontSize.x24b);
        upperContainer.add(cartTitle, "al left");

        clearAllBtn = new JButton("Clear All");
        clearAllBtn.addActionListener(e -> {
            var r = JOptionPane.showConfirmDialog(Main.frame, "Are you sure you want to empty your cart?", "Confirm",JOptionPane.YES_NO_OPTION);
            if(r == 0  ) {
                CartService.getInstance().clearAll();
            }
        });
        upperContainer.add(clearAllBtn, "al right");



        upperContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        new FlatDropShadowBorder(
                                UIManager.getColor("Component.borderColor"),
                                new Insets(0,0,15,0),
                                0.05f
                        ),
                        Padding.byParts(12, 6)
                )
        );

        add(upperContainer, "north, grow, wrap");


        add(new CartContent(), "grow, dock center");

        var southContainer = new JPanel(new MigLayout("align right center", ""));

        cartTotal = new JLabel("Total: P0.0");
        southContainer.add(cartTotal, "al right, grow 0");

        checkoutButton = new JButton("CheckoutModel");
        checkoutButton.setBorder(
                BorderFactory.createCompoundBorder(
                        checkoutButton.getBorder(),
                        Padding.byParts(12,6)
                )
        );
        southContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        new FlatDropShadowBorder(
                                UIManager.getColor("Component.borderColor"),
                                new Insets(15,0,0,0),
                                0.05f
                        ),
                        Padding.byParts(12, 6)
                )
        );
        southContainer.add(checkoutButton, "al right");
        add(southContainer, "dock south");
    }

    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {
    }
}

