package be.ehb.fantasticbeasts.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private int cart_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Setter @Getter
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter @Getter
    private Set<CartItem> cartItems;

    public double total(){
        return cartItems.stream().map(CartItem::subTotal).reduce(0.0, Double::sum);
    }

    public int totalAmount(){
        return cartItems.stream().map(CartItem::getAmount).reduce(0, Integer::sum);
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
