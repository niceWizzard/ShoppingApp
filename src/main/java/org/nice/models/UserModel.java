package org.nice.models;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserModel {
    private final ArrayList<Address> addressesList = new ArrayList<>();

    public Collection<Address> getAddressCollection() {
        return addressesList;
    }

    public Observable<Address> getMainAddress() {
        return mainAddress;
    }

    public Observable<String> getUsername() {
        return username;
    }

    public void setUsername(String n) {
        username.onNext(n);
    }

    private final BehaviorSubject<String> username = BehaviorSubject.createDefault("Username");
    private BehaviorSubject<Address> mainAddress;
    private BehaviorSubject<Collection<Address>> addresses;

    public UserModel() {
        addressesList.add(
                new Address("Your mama", "099392029", "lkjsdkljfa", UUID.randomUUID().toString())
        );
        mainAddress = BehaviorSubject.createDefault(addressesList.get(0));
        addresses = BehaviorSubject.createDefault(addressesList);
    }

}
