package org.nice.models;

public record ProductItemModel(String title, int quantity,float price, String imagePath, String id) {

    public ProductItemModel incrementQuantity() {
        return new ProductItemModel(title, quantity+1, price,imagePath, id);
    }

    public ProductItemModel decrementQuantity() {
        return new ProductItemModel(title, quantity-1, price,imagePath, id);
    }

    public float getTotalPrice() {
        return price * quantity;
    }
}
