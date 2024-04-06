package org.nice.pages.cart;

import com.formdev.flatlaf.ui.FlatLineBorder;
import io.reactivex.rxjava3.disposables.Disposable;
import net.miginfocom.swing.MigLayout;
import org.nice.components.MainButton;
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
    private final Disposable subscription;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

    public CartContent() {
        setLayout(new MigLayout("wrap", "grow, shrink"));
        subscription = CartService.getInstance ().getCartObservable().subscribe(list -> {
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
        setLayout(new MigLayout("gap 12", "grow"));
        setBorder(
                BorderFactory.createCompoundBorder(
                    new FlatLineBorder(new Insets(1,1,1,1), UIManager.getColor("Component.borderColor")),
                    Padding.byParts(12, 8)
                )
        );

        setMaximumSize(new Dimension(720,getMaximumSize().height));
        initComponents();

    }

    private boolean clearConfirmation() {
        return JOptionPane.showConfirmDialog(
                getParent(),
                STR."Are you sure you want to clear the item: \{model.title()}",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        ) == 0;
    }

    private void initComponents() {
        var image = new ImageIcon(Objects.requireNonNull(getClass().getResource(STR."/images/products/\{model.imagePath()}")));
        image.setImage(
                image.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH)
        );
        var imageContainer = new JLabel(image);
        imageContainer.setPreferredSize(new Dimension(120,120));
        add(imageContainer, "dock west");
//-------
        var northContainer = new JPanel(new MigLayout("", "grow"));
        var title = new JLabel(model.title());
        title.setFont(FontSize.x16b);
        northContainer.add(title, "align left");

        var clearBtn = new JButton("Clear");
        northContainer.add(clearBtn, "align right");

        add(northContainer, "dock north");

        clearBtn.addActionListener(_ -> {
            if(!clearConfirmation()) {
                return;
            }
            CartService.getInstance().removeAll(model.id());
        });


//        -------
        var quantityContainer = new JPanel(new MigLayout());
        var quantityLabel = new JLabel(String.valueOf(model.quantity()));
        var decreaseBtn = new JButton("-");
        var increaseBtn = new MainButton("+");

        quantityContainer.add(decreaseBtn);
        quantityContainer.add(quantityLabel);
        quantityContainer.add(increaseBtn);

        var southContainer = new JPanel(new MigLayout("", "grow"));
        southContainer.add(quantityContainer, "align left");

        increaseBtn.addActionListener(e -> {
            CartService.getInstance().add(model);
        });

        decreaseBtn.addActionListener(e -> {
            if(model.quantity() == 1 && !clearConfirmation()) {
                return;
            }
            CartService.getInstance().remove(model.id());
        });

//        -------
        var totalPriceLabel = new JLabel(String.valueOf(model.getTotalPrice()));
        totalPriceLabel.setFont(FontSize.x16b);
        southContainer.add(totalPriceLabel, "align right");

        add(southContainer, "dock south, grow");

        //      =====
        add(new JLabel(String.valueOf(model.price())), "gapx 24");


    }
}