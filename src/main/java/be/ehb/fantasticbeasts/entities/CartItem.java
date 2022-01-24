package be.ehb.fantasticbeasts.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "cart_items")

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private int cartItem_id;

    @ManyToOne
    @Setter @Getter
    private Cart cart;

    @Min(value = 1)
    @Setter @Getter
    private int amount;

    @ManyToOne
    @Setter @Getter
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

    public double subTotal(){
        return product.getPrice() * amount;
    }
}
