package org.nice.pages.cart;

import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.models.ProductItemModel;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartContent extends JPanel {

    private final Map<String, CartItem> cartItemsMap = new HashMap<>();
    public CartContent() {
        setLayout(new MigLayout("wrap", "grow, shrink"));
        CartService.getInstance ().getCartObservable().subscribe(list -> {
            removeAll();
            if(list.isEmpty()) {
                add(new JLabel("Your cart is empty."), "align center center");
            }
            else {
                for(var product : list.values()) {
                    var i = new CartItem(product);
                    add(i, "grow, shrink, al center");
                    cartItemsMap.put(product.id(), i);
                }
            }

            repaint();
            revalidate();


        });
    }
}

class CartItem extends JPanel{
    private final ProductItemModel model;


    public CartItem(ProductItemModel model) {
        this.model = model;
        setLayout(new MigLayout("gap 12"));
        setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x20003), 2, true),
                    Padding.byParts(12, 8)
                )
        );

        setMaximumSize(new Dimension(720,getMaximumSize().height));
        initComponents();

    }

    private void initComponents() {
        var image = new ImageIcon(Objects.requireNonNull(getClass().getResource(STR."/images/products/\{model.imagePath()}")));
        image.setImage(
                image.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH)
        );
        var imageContainer = new JLabel(image);
        imageContainer.setPreferredSize(new Dimension(120,120));
        add(imageContainer, "dock west");

        var title = new JLabel(model.title());
        title.setFont(FontSize.x16b);
        add(title, "dock north,  gapx 20");

        var quantityContainer = new JPanel(new MigLayout());

        var quantityLabel = new JLabel(String.valueOf(model.quantity()));
        var decreaseBtn = new JButton("-");
        var increaseBtn = new JButton("+");
        quantityContainer.add(decreaseBtn);
        quantityContainer.add(quantityLabel);
        quantityContainer.add(increaseBtn);

        add(quantityContainer);

        increaseBtn.addActionListener(e -> {
            CartService.getInstance().add(model);
        });

        decreaseBtn.addActionListener(e -> {
            CartService.getInstance().remove(model.id());
        });


    }
}