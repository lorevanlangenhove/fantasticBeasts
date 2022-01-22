package be.ehb.fantasticbeasts.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "cart_items")

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItem_id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Order order;

    @Min(value = 1)
    private int amount;

    @ManyToOne
    private Product product;

    public CartItem() {
    }

    public CartItem(int amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    public CartItem(Cart cart, int amount, Product product) {
        this.cart = cart;
        this.amount = amount;
        this.product = product;
    }

    public int getCartItem_id() {
        return cartItem_id;
    }

    public void setCartItem_id(int cartItem_id) {
        this.cartItem_id = cartItem_id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double subTotal(){
        return product.getPrice() * amount;
    }
}
