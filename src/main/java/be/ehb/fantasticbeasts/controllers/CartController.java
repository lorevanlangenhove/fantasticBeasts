package be.ehb.fantasticbeasts.controllers;

import be.ehb.fantasticbeasts.entities.Cart;
import be.ehb.fantasticbeasts.entities.CartItem;
import be.ehb.fantasticbeasts.entities.Product;
import be.ehb.fantasticbeasts.repo.CartItemRepo;
import be.ehb.fantasticbeasts.repo.CartRepo;
import be.ehb.fantasticbeasts.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    ProductRepo productRepo;
    CartItemRepo cartItemRepo;
    CartRepo cartRepo;
    OidcUser principal;

    @Autowired
    public CartController(ProductRepo productRepo, CartItemRepo cartItemRepo, CartRepo cartRepo) {
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
        this.cartRepo = cartRepo;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(){
        return "cart";
    }

    @ModelAttribute("allItems")
    public Iterable<CartItem> findAllItems(){
        return cartItemRepo.findAll();
    }

    @ModelAttribute("allCarts")
    public Iterable<Cart> findAllCarts(){
        return cartRepo.findAll();
    }

    @RequestMapping(value = "/cart/add/{prodId}", method = RequestMethod.GET)
    public String addProduct(@PathVariable(value = "prodId")int prodId, @AuthenticationPrincipal OidcUser principal){
        Product product = productRepo.findById(prodId).orElse(null);
        if (product == null) {
            return null;
        }

        if(principal == null){
            return null;
        }

        String email = principal.getEmail();
        Cart cart = cartRepo.findByUserEmail(email).orElse(null);

        CartItem item = getItemById(cart, prodId);
        if (item == null) {
            item = new CartItem(cart, 1, product);
            cart.addCartItem(item);
        } else {
            item.setAmount(item.getAmount() + 1);
        }
        cartRepo.save(cart);
        System.out.println(cart);
        return "cart";
    }

    @RequestMapping(value = "/cart/delete/{prodId}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable(value = "prodId")int prodId, @AuthenticationPrincipal OidcUser principal){

        if(!productRepo.existsById(prodId)){
            return null;
        }

        if(principal == null){
            return null;
        }

        String email = principal.getEmail();
        Cart cart = cartRepo.findByUserEmail(email).orElse(null);
        cart.getCartItems().removeIf(item -> item.getProduct().getProduct_id() == prodId);
        cartRepo.save(cart);
        return "cart";
    }

    public CartItem getItemById(Cart cart, int prodId){
        return cart.getCartItems().stream().filter(item -> item.getProduct().getProduct_id() == prodId).findFirst().orElse(null);
    }
}