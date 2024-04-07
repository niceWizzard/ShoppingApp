package org.nice.pages.home;

import com.formdev.flatlaf.ui.FlatLineBorder;
import net.miginfocom.swing.MigLayout;
import org.nice.components.MainButton;
import org.nice.constants.FontSize;
import org.nice.models.ProductItemModel;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ProductItem extends JPanel {

    private final ProductItemModel model;

    public ProductItem(ProductItemModel model) {
        this.model = model;
        setLayout(new MigLayout("wrap, align center top"));
        setPreferredSize(new Dimension(160, 180));
        setBorder(
                new FlatLineBorder(
                        new Insets(1,1,1,1),
                        UIManager.getColor("Component.borderColor")
                )
        );

        var title = new JLabel(model.title());
        title.setFont(FontSize.x18b);
        add(title, "grow 0");


        var i = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/products/"+model.imagePath())));
        var scaled = i.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH);
        i.setImage(scaled);
        var img = new JLabel(i);
        img.setMaximumSize(new Dimension(120   ,120));
        add(img, "al center");

        var price = new JLabel("P " +model.price());
        add(price, "al right");

        var addToCart = new MainButton("Add to Cart");

        add(addToCart, "grow");

        addToCart.addActionListener(e -> {
            CartService.getInstance().add(model);
        });

    }
}
