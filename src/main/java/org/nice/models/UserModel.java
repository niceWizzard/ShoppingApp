package org.nice.models;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

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

    public Observable<Collection<Address>> getAddresses() {
        return addresses;
    }

    private BehaviorSubject<Collection<Address>> addresses;

    public Observable<Address> getOnAddressUpdated() {
        return onAddressUpdated;
    }

    private final Subject<Address> onAddressUpdated = PublishSubject.create();



    public void removeAddress(String id) {
        addressesList.removeIf(v -> v.id().equals(id));
        addresses.onNext(addressesList);
    }

    public void updateAddress(Address add) {
        var i = 0;
        for(var a : addressesList) {
            if(add.id().equals(a.id())) {
                break;
            }
            i++;
        }
        addressesList.set(i, add);
        addresses.onNext(addressesList);
        onAddressUpdated.onNext(add);
    }

    public void addAddress(Address address) {
        var alreadyExists = addressesList.stream().filter(v -> v.id().equals(address.id()));
        if(alreadyExists.findAny().isEmpty()) {
            addressesList.add(address);
            addresses.onNext(addressesList);
        }
    }

    public UserModel() {
        for(int i=0; i < 5; i++ ) {
            addressesList.add(
                    new Address("Your mama", "099392029", "lkjsdkljfa", UUID.randomUUID().toString())
            );
        }
        mainAddress = BehaviorSubject.createDefault(addressesList.get(0));
        addresses = BehaviorSubject.createDefault(addressesList);
    }

}
