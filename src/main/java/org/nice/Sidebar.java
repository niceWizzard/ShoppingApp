package org.nice;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import net.miginfocom.swing.MigLayout;
import org.nice.constants.FontSize;
import org.nice.constants.Padding;
import org.nice.services.CartService;
import org.nice.services.NavigationService;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Sidebar extends JPanel {


    private final Disposable subscription;
    private SidebarLink cartLink= new SidebarLink("Cart", Main.NAV_CART);

    @Override
    public void removeNotify() {
        super.removeNotify();
        subscription.dispose();
    }
    public Sidebar() {
        init();
        initComponents();
        subscription = CartService.getInstance().getCartObservable().map(Map::values).subscribe(items -> {
            if(items.isEmpty()) {
                cartLink.setText("Cart");
                return ;
            }
            var total = 0;
            for(var v : items) {
                total += v.quantity();
            }
           cartLink.setText(MessageFormat.format("Cart ({0})", total));

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
        setFont(FontSize.x16);
        setOpaque(false);
        setPreferredSize(new Dimension(1080, 36));
        addActionListener(e -> Main.navigation.navigateTo(route));

        NavigationService.getInstance().nav.onNavigationChange.addListener(active -> {
            if(Objects.equals(active.route(), route)) {
                setBackground(UIManager.getColor("Button.default.background"));
                setForeground(UIManager.getColor("Button.default.foreground"));
            } else{
                setBackground(UIManager.getColor("Button.background"));
                setForeground(UIManager.getColor("Button.foreground"));

            }
        });

    }

}