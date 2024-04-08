package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.nice.models.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class AddressService {
    private static AddressService instance;

    public static AddressService getInstance() {
        return instance;
    }
    public AddressService() {
        instance = this;
        for(int i=0; i < 5; i++ ) {
            addressesList.add(
                    new Address("Your mama", "099392029", "lkjsdkljfa", UUID.randomUUID().toString())
            );
        }
        mainAddress = BehaviorSubject.createDefault(addressesList.get(0));
        addresses = BehaviorSubject.createDefault(addressesList);
    }

    private final ArrayList<Address> addressesList = new ArrayList<>();

    public Collection<Address> getAddressCollection() {
        return addressesList;
    }
    public Observable<Address> getMainAddressObservable() {
        return Observable.wrap(mainAddress);
    }
    public Observable<Address> getMainAddress() {
        return mainAddress;
    }

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

}
