package org.nice.services;

import org.nice.navigation.NavigationPanel;

public class ServiceManager {
    public ServiceManager() {
        new CartService();
        new UserService();
        new AddressService();
        new CheckoutService();
    }
}
