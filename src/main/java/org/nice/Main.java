package org.nice;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
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

        root.add(sidebar, "east");

    }
}