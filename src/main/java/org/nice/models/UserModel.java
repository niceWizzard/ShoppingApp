package org.nice.models;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserModel {
    public Observable<String> getUsername() {
        return username;
    }

    public void setUsername(String n) {
        username.onNext(n);
    }

    private final BehaviorSubject<String> username = BehaviorSubject.createDefault("Username");


    public UserModel() {

    }

}
