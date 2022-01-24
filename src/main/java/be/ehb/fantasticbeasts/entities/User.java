package be.ehb.fantasticbeasts.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private int user_id;

    @NotBlank
    @Setter @Getter
    private String username;

    @Setter @Getter
    private String email;

    @NotBlank
    @Setter @Getter
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @Setter @Getter
    private Cart cart;

    public User(){
        cart = new Cart();
        cart.setUser(this);
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
