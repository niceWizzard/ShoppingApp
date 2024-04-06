package org.nice.pages.home;

import net.miginfocom.swing.MigLayout;
import org.nice.models.ProductItemModel;
import org.nice.navigation.Routeable;

import javax.swing.*;

public class HomePage extends Routeable {
    final ProductItemModel[] products = new ProductItemModel[]{
            new ProductItemModel("Nig** shoe",1, 420, "black_shoes.jpg", "lkjalksdjDSAFLK"),
            new ProductItemModel("Blue Shoe",1, 420, "blue_shoes.jpg", "LSDKJFLKSDEN"),
            new ProductItemModel("Crocs Shoe",1, 420, "crocs.jpg", "LKSJDILFE"),
            new ProductItemModel("Red Shoe",1, 420, "red_shoe.jpg", "LKSDOKW1289jk"),
            new ProductItemModel("Vomero 17 Pro Max",1, 420, "vomero.png", "LSKDJILFLKJE"),
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
