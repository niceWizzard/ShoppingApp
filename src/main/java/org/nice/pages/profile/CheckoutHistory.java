package org.nice.pages.profile;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.listview.DynamicListView;
import org.nice.listview.Item;
import org.nice.models.CheckoutModel;
import org.nice.services.CheckoutService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class CheckoutHistory extends JPanel {
    private DynamicListView<CheckoutModel> listView = new DynamicListView<>(
            CheckoutService.getInstance().getCheckoutList(),
            CheckoutModel::id,
            (item) -> {
                var view = new JPanel(new MigLayout("", "grow"));
                view.setBorder(
                        BorderFactory.createCompoundBorder(
                                new FlatRoundBorder(),
                                Padding.byParts(12,6)
                        )
                );
                var date = new JLabel(item.date().toString());
                var totalPrice = new JLabel(String.valueOf(item.totalPrice()));
                date.setFont(FontSize.x16b);
                totalPrice.setFont(FontSize.x18b);

                view.add(date, "al left, grow");
                view.add(totalPrice , "wrap 24, align right");
                for(var prod : item.products()) {
                    view.add(new JLabel(prod.title() + " x" + prod.quantity()), "al left, grow");
                    view.add(new JLabel(String.valueOf(prod.getTotalPrice())), "al right, wrap");
                }
                return new Item<>(view, Optional.of("grow"));
            },
            new Item<>(new JLabel("You have no checkouts."), Optional.empty()),
            new MigLayout("wrap", "grow")
    );
    public CheckoutHistory() {
        setLayout(new MigLayout("debug", "grow", "grow"));
        final var title = new JLabel("Your checkout history");
        title.setFont(FontSize.x16b);
        title.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,1,0,
                                UIManager.getColor("Component.borderColor")),
                        Padding.byParts(8,4)
                )
        );
        add(title, "wrap, grow");


        var scroll = new JScrollPane(listView);
        scroll.setPreferredSize(new Dimension(720,720));
        scroll.setBorder(null);
        add(scroll, "grow");

    }
}
