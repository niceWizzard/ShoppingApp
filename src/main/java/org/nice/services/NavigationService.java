package org.nice.services;

import org.nice.navigation.NavigationPanel;

public class NavigationService {
    private static NavigationService instance;
    public final NavigationPanel nav;

    public static NavigationService getInstance(){return instance;}

    public NavigationService(NavigationPanel nav) {
        instance = this;
        this.nav = nav;
    }




}
