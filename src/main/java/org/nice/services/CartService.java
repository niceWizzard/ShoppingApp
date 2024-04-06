package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.nice.models.ProductItemModel;

import java.util.ArrayList;
import java.util.Objects;

public class CartService {
    private static CartService instance;

    public static CartService getInstance(){return instance; }

    private BehaviorSubject<ArrayList<ProductItemModel>> cartList =
            BehaviorSubject
                    .createDefault(new ArrayList<>());
    private ArrayList<ProductItemModel> list = cartList.getValue();

    public Observable<ArrayList<ProductItemModel>> getCartObservable() {
        return Observable.wrap(cartList);
    }

    public void addToCart(ProductItemModel item) {
        list.add(item);

        cartList.onNext(list);
    }

    public void remove(String id) {
        list.removeIf(e -> Objects.equals(e.id(), id));
        cartList.onNext(list);
    }

    public CartService() {
        instance = this;
    }




}
