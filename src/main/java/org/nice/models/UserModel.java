package org.nice.models;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.UUID;

public class UserModel {
    private final ArrayList<Address> addresses = new ArrayList<>();

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

    public UserModel() {
        addresses.add(
                new Address("Your mama", "099392029", "lkjsdkljfa", UUID.randomUUID().toString())
        );
        mainAddress = BehaviorSubject.createDefault(addresses.get(0));
    }

}
