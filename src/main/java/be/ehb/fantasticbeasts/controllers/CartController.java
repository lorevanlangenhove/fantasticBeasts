package be.ehb.fantasticbeasts.controllers;

import be.ehb.fantasticbeasts.entities.Cart;
import be.ehb.fantasticbeasts.entities.CartItem;
import be.ehb.fantasticbeasts.entities.Product;
import be.ehb.fantasticbeasts.entities.User;
import be.ehb.fantasticbeasts.repo.CartItemRepo;
import be.ehb.fantasticbeasts.repo.CartRepo;
import be.ehb.fantasticbeasts.repo.ProductRepo;
import be.ehb.fantasticbeasts.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class CartController {
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    @Autowired
    public CartController(ProductRepo productRepo, CartItemRepo cartItemRepo, CartRepo cartRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
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
    public Iterable<Cart> findAllCarts(@AuthenticationPrincipal OidcUser principal){
        ArrayList<Cart> carts = new ArrayList<>();
        carts.add(cartRepo.findByUserEmail(principal.getEmail()).orElse(null));
        return carts;
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
        if(userRepo.findByEmail(email) == null){
            User user = new User();
            user.setUsername(principal.getNickName());
            user.setEmail(email);
            userRepo.save(user);
        }

        Cart cart = cartRepo.findByUserEmail(email).orElse(null);
        if(cart == null){
            cart = new Cart();
            cart.setUser(userRepo.findByEmail(principal.getEmail()));
        }

        CartItem item = getItemById(cart, prodId);
        if (item == null) {
            item = new CartItem(cart, 1, product);
            cart.addCartItem(item);
        } else {
            item.setAmount(item.getAmount() + 1);
        }

        cartRepo.save(cart);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/delete/{prodId}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable(value = "prodId")int prodId, @AuthenticationPrincipal OidcUser principal, RedirectAttributes redirectAttributes){
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
        redirectAttributes.addFlashAttribute("alert", "Product has been successfully removed from your cart");
        redirectAttributes.addFlashAttribute("alertType", "Success");
        return "cart";
    }

    public CartItem getItemById(Cart cart, int prodId){
        return cart.getCartItems().stream().filter(item -> item.getProduct().getProduct_id() == prodId).findFirst().orElse(null);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(@AuthenticationPrincipal OidcUser principal, RedirectAttributes redirectAttributes){
        if(principal == null){
            return null;
        }
        String email = principal.getEmail();
        Cart cart = cartRepo.findByUserEmail(email).orElse(null);
        cart.empty();
        cartRepo.save(cart);

        redirectAttributes.addFlashAttribute("alert", "Cart is empty!");
        redirectAttributes.addFlashAttribute("alertType", "Success");

        return "order";
    }
}