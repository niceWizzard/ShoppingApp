package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import org.nice.models.Address;
import org.nice.models.UserModel;

public class UserService {

    public static UserService getInstance() {
        return instance;
    }
    private static UserService instance;

    public UserModel getCurrentUser() {
        return currentUser;
    }

    private final UserModel currentUser = new UserModel() ;

    public void setUsername(String n) {
        currentUser.setUsername(n);
    }

    public Observable<Address> getMainAddressObservable() {
        return Observable.wrap(currentUser.getMainAddress());
    }

    public UserService() {
        instance = this;
    }





}
