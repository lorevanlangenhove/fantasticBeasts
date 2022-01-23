package be.ehb.fantasticbeasts.controllers;

import be.ehb.fantasticbeasts.entities.Product;
import be.ehb.fantasticbeasts.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class ProductController {

    ProductRepo repo;

    @Autowired
    public ProductController(ProductRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index (Model model, @AuthenticationPrincipal OidcUser principal){
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }

    @RequestMapping(value = "/{prodId}", method = RequestMethod.GET)
    public String getProduct(ModelMap modelMap, @PathVariable(value = "prodId")int prodId){
        Optional<Product> optionalProduct = repo.findById(prodId);
        if(optionalProduct.isPresent()){
            modelMap.addAttribute("product", optionalProduct.get());
            return "detailsProduct";
        }
        return "redirect:../index";
    }

    @ModelAttribute("product")
    public Product product(){
        return new Product();
    }

    @ModelAttribute("all")
    public Iterable<Product> findAll(){
        return repo.findAll();
    }

    @RequestMapping(value = "/food", method = RequestMethod.GET)
    public String food (){
        return "food";
    }

    @RequestMapping(value = "/habitat", method = RequestMethod.GET)
    public String habitat (){
        return "habitat";
    }

    @RequestMapping(value = "/toys", method = RequestMethod.GET)
    public String toys (){
        return "toys";
    }
}
