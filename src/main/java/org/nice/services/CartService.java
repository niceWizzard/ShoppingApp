package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.nice.models.ProductItemModel;

import java.util.HashMap;
import java.util.Map;

public class CartService {
    private static CartService instance;

    public static CartService getInstance(){return instance; }

    private BehaviorSubject<Map<String,ProductItemModel>> cartList =
            BehaviorSubject
                    .createDefault(new HashMap<>());
    private final Map<String,ProductItemModel> list = cartList.getValue();

    public Observable<Map<String,ProductItemModel>> getCartObservable() {
        return Observable.wrap(cartList);
    }

    public void add(ProductItemModel item) {
        var existing = list.get(item.id());
        if(existing != null) {
            list.replace(item.id(), existing.incrementQuantity());
        } else{
            list.put(item.id(), item);
        }
        cartList.onNext(list);
    }

    public void remove(String id) {
        var existingProduct = list.get(id);
        if(existingProduct != null) {
            existingProduct.decrementQuantity();
            cartList.onNext(list);
        }
    }

    public CartService() {
        instance = this;
    }




}
