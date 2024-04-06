package org.nice.pages.profile;

import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;

import javax.swing.*;
import java.awt.*;

public class CheckoutHistory extends JPanel {
    public CheckoutHistory() {
        setLayout(new MigLayout("", "grow"));
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


        var checkoutsContainer = new JPanel();
        var scroll = new JScrollPane(checkoutsContainer);
        scroll.setPreferredSize(new Dimension(720,720));
        scroll.setBorder(null);
        add(scroll);

        checkoutsContainer.add(new JLabel("You don't have any purchases yet."));
    }
}
