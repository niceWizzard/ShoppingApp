package org.nice.pages.cart;

import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.navigation.Routeable;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;

public class CartPage extends Routeable {
    private JLabel cartTotal;

    public CartPage() {
        init();
        CartService.getInstance().getCartObservable().subscribe(cartList  -> {
            var totalPrice = 0f;
            for(var p : cartList.values()) {
                totalPrice += p.price();
            }

            cartTotal.setText(STR."Total: P\{totalPrice}");

        });
    }

    private void init() {
        setBorder(Padding.byParts(24, 12));
        setLayout(new MigLayout("", "grow"));

        var upperContainer = new JPanel(new MigLayout("", "[grow]", "[grow]"));

        var cartTitle = new JLabel("Your Cart");
        cartTitle.setFont(FontSize.x24b);
        upperContainer.add(cartTitle, "al left");

        cartTotal = new JLabel("Total: P0.0");
        upperContainer.add(cartTotal, "al right, wrap");
        upperContainer.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, new Color(0x00023)));

        add(upperContainer, "north, grow, wrap");

        var scrollPane = new JScrollPane(new CartContent());
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(1080,1080));
        add(scrollPane, "grow");
    }

    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {
    }
}

