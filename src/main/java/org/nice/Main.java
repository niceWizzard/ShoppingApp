package org.nice;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import net.miginfocom.swing.MigLayout;
import org.nice.navigation.NavRoute;
import org.nice.navigation.NavigationPanel;
import org.nice.pages.addressmanage.ManageAddressPage;
import org.nice.pages.cart.CartPage;
import org.nice.pages.home.HomePage;
import org.nice.pages.profile.ProfilePage;
import org.nice.services.NavigationService;
import org.nice.services.ServiceManager;



import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static NavigationPanel navigation;

    public static JFrame frame;

    public static final String NAV_HOME = "home";
    public static final String NAV_PROFILE = "profile";
    public static final String NAV_CART = "cart";
    public static final String NAV_MANAGE_ADDRESSES = "address/edit";

    public static void main(String[] args) {
        new Main();
    }

    public  Main() {
        init();
    }

    private void init() {
        frame = this;
        FlatMacLightLaf.setup();
        var s = new ServiceManager();
        var root=new JPanel(new MigLayout());
        add(root);

        initComponents(root);

        setSize(new Dimension(1080, 720));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void initComponents(JPanel root) {
        var mainContent = new NavigationPanel(new NavRoute[]{
                new NavRoute(new CartPage(), NAV_CART),
                new NavRoute(new HomePage(), NAV_HOME),
                new NavRoute(new ProfilePage(), NAV_PROFILE),
                new NavRoute(new ManageAddressPage(), NAV_MANAGE_ADDRESSES)
        });
        new NavigationService(mainContent);

        var sidebar = new Sidebar(root);

        navigation = mainContent;
        root.add(mainContent, "dock center");
        root.add(sidebar, "dock west");

    }
}