package org.nice.pages.home;

import net.miginfocom.swing.MigLayout;
import org.nice.models.ProductItemModel;
import org.nice.navigation.Routeable;

import javax.swing.*;

public class HomePage extends Routeable {
    final ProductItemModel[] products = new ProductItemModel[]{
            new ProductItemModel("Nig** shoe", 420, "black_shoes.jpg"),
            new ProductItemModel("Blue Shoe", 420, "blue_shoes.jpg"),
            new ProductItemModel("Crocs Shoe", 420, "crocs.jpg"),
            new ProductItemModel("Red Shoe", 420, "red_shoe.jpg"),
            new ProductItemModel("Vomero 17 Pro Max", 420, "vomero.png"),
    };
    public HomePage() {
        setLayout(new MigLayout());
        add(new JLabel("HomePage"), "wrap");
        for(var shoe : products) {
            add(new ProductItem(shoe));
        }
    }
    @Override
    public void onNavigationEnter(Object... objects) {

    }

    @Override
    public void onNavigationExit(String s) {

    }
}
