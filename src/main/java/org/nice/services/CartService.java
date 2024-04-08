package org.nice.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.nice.models.ProductItemModel;

import java.util.HashMap;
import java.util.Map;

public class CartService {
    private static CartService instance;

    public static CartService getInstance(){return instance; }

    private BehaviorSubject<Map<String,ProductItemModel>> cartItemsMap =
            BehaviorSubject
                    .createDefault(new HashMap<>());

    public Map<String, ProductItemModel> getItemMap() {
        return itemMap;
    }

    private final Map<String,ProductItemModel> itemMap = cartItemsMap.getValue();

    public Observable<Map<String,ProductItemModel>> getCartObservable() {
        return Observable.wrap(cartItemsMap);
    }

    public void add(ProductItemModel item) {
        var existing = itemMap.get(item.id());
        if(existing != null) {
            itemMap.replace(item.id(), existing.incrementQuantity());
        } else{
            itemMap.put(item.id(), item);
        }
        cartItemsMap.onNext(itemMap);
    }

    public void clearAll() {
        assert itemMap != null;
        itemMap.clear();
        cartItemsMap.onNext(itemMap);
    }

    public void remove(String id) {
        assert itemMap != null;
        var existingProduct = itemMap.get(id);
        if (existingProduct == null) {
            return;
        }
        var newProduct = existingProduct.decrementQuantity();
        if(newProduct.quantity() == 0) {
            itemMap.remove(newProduct.id());
        } else {
            itemMap.replace(newProduct.id(), newProduct);
        }
        cartItemsMap.onNext(itemMap);
    }

    public CartService() {
        instance = this;
    }


    public void removeAll(String id) {
        assert itemMap != null;
        itemMap.remove(id);
        cartItemsMap.onNext(itemMap);
    }
}
