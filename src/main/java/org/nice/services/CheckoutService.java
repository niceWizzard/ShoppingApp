package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.nice.models.CheckoutModel;
import org.nice.models.ProductItemModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CheckoutService {
    private static CheckoutService instance;

    public Collection<CheckoutModel> getCheckoutList() {
        return checkoutList;
    }

    private ArrayList<CheckoutModel> checkoutList = new ArrayList<>();

    public Observable<Collection<CheckoutModel>> getCheckoutHistory() {
        return Observable.wrap(checkoutHistory);
    }

    private BehaviorSubject<Collection<CheckoutModel>> checkoutHistory = BehaviorSubject
            .createDefault(checkoutList);



    public static CheckoutService getInstance() {
        return instance;
    }

    public void addToHistory(CheckoutModel checkout) {
        checkoutList.add(checkout);
        checkoutHistory.onNext(checkoutList);
    }




    public CheckoutService() {
        instance = this;
        checkoutList.add(new CheckoutModel(List.of(new ProductItemModel[]{
                new ProductItemModel("Nice", 2, 120, "crocs.jpg", "lkDkml2k3dmd")
        }),
                140.0f,
                new Date(),
                "kldssfdi2lksdME"
        ));
    }



}
