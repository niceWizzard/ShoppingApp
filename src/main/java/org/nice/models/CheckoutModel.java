package org.nice.models;

import java.util.Collection;
import java.util.Date;

public record CheckoutModel(Collection<ProductItemModel> products, float totalPrice,Date date, String id) {
}
