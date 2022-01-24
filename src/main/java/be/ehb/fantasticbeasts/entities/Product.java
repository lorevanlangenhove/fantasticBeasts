package be.ehb.fantasticbeasts.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private int product_id;

    @NotBlank
    @Setter @Getter

    private String name;

    @NotBlank
    @Setter @Getter
    private String description;

    @DecimalMin(value = "0.0")
    @Setter @Getter
    private double price;

    @Setter @Getter
    private String category;

}
