package be.ehb.fantasticbeasts.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public double total(){
        return cartItems.stream().map(CartItem::subTotal).reduce(0.0, Double::sum);
    }

    public int totalAmount(){
        return cartItems.stream().map(CartItem::getAmount).reduce(0, Integer::sum);
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
    }

    public void removeCartItem(CartItem cartItem){
        cartItems.remove(cartItem);
    }

    public void addProduct(Product product, int amount){
        CartItem cartItem = new CartItem(amount, product);
        cartItems.add(cartItem);
    }

    public void empty(){
        cartItems.clear();
    }
}
