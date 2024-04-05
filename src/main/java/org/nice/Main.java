package org.nice;

import net.miginfocom.swing.MigLayout;
import org.nice.navigation.NavRoute;
import org.nice.navigation.NavigationPanel;
import org.nice.pages.CartPage;
import org.nice.pages.HomePage;
import org.nice.pages.ProfilePage;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static NavigationPanel navigation;

    public static final String NAV_HOME = "home";
    public static final String NAV_PROFILE = "profile";
    public static final String NAV_CART = "cart";

    public static void main(String[] args) {
        new Main();
    }

    public  Main() {
        init();
    }

    private void init() {
        var root=new JPanel(new MigLayout());
        add(root);

        initComponents(root);

        setSize(new Dimension(1080, 720));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents(JPanel root) {
        var sidebar = new Sidebar();
        var mainContent = new NavigationPanel(new NavRoute[]{
                new NavRoute(new CartPage(), NAV_CART),
                new NavRoute(new HomePage(), NAV_HOME),
                new NavRoute(new ProfilePage(), NAV_PROFILE)
        });
        navigation = mainContent;
        root.add(mainContent, "dock center");
        root.add(sidebar, "dock west");

    }
}