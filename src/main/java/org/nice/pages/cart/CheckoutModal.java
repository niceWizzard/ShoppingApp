package org.nice.pages.cart;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import net.miginfocom.swing.MigLayout;
import org.nice.Main;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.listview.DynamicListView;
import org.nice.listview.Item;
import org.nice.models.CheckoutModel;
import org.nice.models.ProductItemModel;
import org.nice.services.CartService;
import org.nice.services.CheckoutService;
import org.nice.services.NavigationService;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.Optional;

public class CheckoutModal extends JDialog {
    private DynamicListView<ProductItemModel> listView;



    public CheckoutModal(CheckoutModel model) {
        setModalityType(ModalityType.APPLICATION_MODAL);
        init(model);
        setTitle("Checkout");
        setMinimumSize(new Dimension(360, 240));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(Main.frame);
        setVisible(true);
    }

    private void init(CheckoutModel model) {
        setLayout(new MigLayout("insets 24", "grow"));
        listView = new DynamicListView<>(
                model.products(),
                ProductItemModel::id,
                (item) -> {
                    final var view = new JPanel(new MigLayout("", "grow"));
                    view.add(new JLabel(MessageFormat.format("{0} x{1}", item.title(), item.quantity())), "al left, grow");
                    view.add(new JLabel(String.valueOf(item.getTotalPrice())), "al right, wrap");
                    return new Item<>(view, Optional.of("grow, wrap"));
                },
                new Item<>(new JLabel("You have no checkouts."), Optional.empty()),
                new MigLayout("wrap", "grow")
        );
        var scroll = new JScrollPane(listView);
        add(scroll, "grow, wrap");

        var btn = new JButton("Order now");
        add(btn, "grow");
        btn.addActionListener(v -> {
            CheckoutService.getInstance().addToHistory(model);
            CartService.getInstance().clearAll();
            NavigationService.getInstance().nav.navigateTo(Main.NAV_PROFILE);
            dispose();
        });

    }
}
