package org.nice;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.services.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Sidebar extends JPanel {


    private SidebarLink cartLink= new SidebarLink("Cart", Main.NAV_CART);
    public Sidebar() {
        init();
        initComponents();
        CartService.getInstance().getCartObservable().map(v -> v.size()).subscribe(v -> {
            if(v == 0) {
                cartLink.setText("Cart");
                return ;
            }
           cartLink.setText(STR."Cart (\{v.toString()})");

        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        var c1 = new Color(0x02020);
        var c2 = new Color(0x93999);
        var gradient = new GradientPaint(0,0,c1, w, h, c2);
        graphics.setPaint(gradient);
        graphics.fillRect(0,0, w, h);
    }

    private void init() {
        setLayout(new MigLayout("wrap, align center top"));
        setPreferredSize(new Dimension(180, 720 ));
        setBorder(Padding.byParts(12, 6));
    }

    private void initComponents() {
        var label = new JLabel("Shopping App");
        label.setFont(FontSize.x24b);
        label.setForeground(new Color(0xFFE6FC));
        add(label, "wrap 36");


        add(cartLink, "grow");
        add(new SidebarLink("Profile", Main.NAV_PROFILE), "grow");
        add(new SidebarLink("Home", Main.NAV_HOME), "grow");




    }
}


class SidebarLink extends JButton {

    public SidebarLink(String text, String route) {
        setText(text);
        setFont(FontSize.x16b);
        setOpaque(false);
        setPreferredSize(new Dimension(1080, 36));
        addActionListener(e -> Main.navigation.navigateTo(route));
    }

}