package org.nice.pages.cart;

import com.formdev.flatlaf.ui.FlatLineBorder;
import io.reactivex.rxjava3.disposables.Disposable;
import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.components.MainButton;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.listview.DynamicListView;
import org.nice.listview.Item;
import org.nice.models.ProductItemModel;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CartContent extends JPanel {

    private final ArrayList<ProductItemModel> items = new ArrayList<>();
    private final Disposable subscription;

    public final DynamicListView<ProductItemModel> listView = new DynamicListView<>(
            items,
            ProductItemModel::id,
            (item) -> {
                var view = new CartItem(item);
                return new Item<>(view, Optional.of("grow, al center"));
            },
            new Item<>(
                    new JLabel("Your cart is empty."),
                    Optional.of("align center center")
            ),
            new MigLayout("wrap", "grow")

    );

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }

    public CartContent() {
        setLayout(new MigLayout("", "grow", "grow"));
        var scrollPane = new JScrollPane(listView);
        add(scrollPane,"grow");
        subscription = CartService.getInstance ().getCartObservable().subscribe(list -> {
            items.clear();
            items.addAll(list.values());
            listView.update();
        });
    }
}

class CartItem extends JPanel{
    private ProductItemModel model;
    private JLabel quantityLabel;
    private JLabel totalPriceLabel;
    private Disposable subscription;

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }


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
                Main.frame,
                "Are you sure you want to clear the item: " +model.title(),
                "Confirm",
                JOptionPane.YES_NO_OPTION
        ) == 0;
    }

    private void initComponents() {
        var image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/products/" +model.imagePath())));
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
        clearBtn.setBackground(
                UIManager.getColor("Component.error.focusedBorderColor")
        );
        clearBtn.setForeground(
                UIManager.getColor("Button.default.foreground")
        );
        northContainer.add(clearBtn, "align right");

        add(northContainer, "dock north");

        clearBtn.addActionListener(a -> {
            if(!clearConfirmation()) {
                return;
            }
            CartService.getInstance().removeAll(model.id());
        });


//        -------
        var quantityContainer = new JPanel(new MigLayout());
        quantityLabel = new JLabel(String.valueOf(model.quantity()));
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
        totalPriceLabel = new JLabel(String.valueOf(model.getTotalPrice()));
        totalPriceLabel.setFont(FontSize.x16b);
        southContainer.add(totalPriceLabel, "align right");

        add(southContainer, "dock south, grow");

        //      =====
        add(new JLabel(String.valueOf(model.price())), "gapx 24");

        this.subscription = CartService.getInstance().getCartObservable()
                .map(v -> v.get(model.id()))
                .subscribe(v -> {
                    this.model = v;
                    this.quantityLabel.setText(String.valueOf(model.quantity()));
                    this.totalPriceLabel.setText(String.valueOf(model.getTotalPrice()));
                });
    }
}