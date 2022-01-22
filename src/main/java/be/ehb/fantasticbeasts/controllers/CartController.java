package be.ehb.fantasticbeasts.controllers;

import be.ehb.fantasticbeasts.entities.Cart;
import be.ehb.fantasticbeasts.entities.CartItem;
import be.ehb.fantasticbeasts.entities.Product;
import be.ehb.fantasticbeasts.repo.CartRepo;
import be.ehb.fantasticbeasts.repo.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class CartController {
    ProductRepo productRepo;
    CartRepo cartRepo;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(){
        return "cart";
    }

    /*@RequestMapping(value = "/cart/buy/{prodId}", method = RequestMethod.GET)
    public String buy(@PathVariable("prodId") int prodId, HttpSession session){
        Cart cart = new Cart();
        Product product = productRepo.findById(prodId).orElse(null);
        cart.addProduct(product, 1);
        return "";
    }*/



   /* public CartController(ProductRepo productRepo, CartRepo cartRepo) {
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
    }

    private Cart getCurrentCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return cartRepo.findByUserUsername(username).orElse(null);
    }

    @RequestMapping(value = "/cart")
    public ResponseEntity<Cart> getCart(){
        return ResponseEntity.ok(getCurrentCart());
    }

    @PostMapping("/cart/add/{prodId}/{amount}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable int prodId, @PathVariable int amount){
        Cart cart = saveItem(prodId, amount);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/cart/update/{prodId}/{amount}")
    public ResponseEntity<Cart> updateAmount(@PathVariable int prodId, @PathVariable int amount){
        if(!productRepo.existsById(prodId)){
            return ResponseEntity.notFound().build();
        }

        Cart cart = getCurrentCart();
        CartItem cartItem = getCartItemById(cart, prodId);
        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }

        cartItem.setAmount(amount);
        cartRepo.save(cart);

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/cart/delete/{prodId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable int prodId){
        if(!productRepo.existsById(prodId)){
            return ResponseEntity.notFound().build();
        }

        Cart cart = getCurrentCart();
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getProduct_id() == prodId);
        return ResponseEntity.ok(cartRepo.save(cart));
    }

    private Cart saveItem(int prodId, int amount) {
        Product product = productRepo.findById(prodId).orElse(null);
        if(product == null){
            return null;
        }

        Cart cart = getCurrentCart();
        CartItem cartItem = getCartItemById(cart, prodId);
        if(cartItem == null){
            cartItem = new CartItem(cart, amount, product);
            cart.addCartItem(cartItem);
        }else{
            cartItem.setAmount(cartItem.getAmount() + amount);
        }
        return cartRepo.save(cart);
    }

    private CartItem getCartItemById(Cart cart, int prodId) {
        return cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().getProduct_id() == prodId).findFirst().orElse(null);
    }*/
}
