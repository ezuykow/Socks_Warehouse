package ru.ezuykow.socks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ezuykow
 */
@Entity
@Table(name = "socks")
@Data
@NoArgsConstructor
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "color")
    private String color;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "cotton_part")
    private Integer cottonPart;

    public Socks(String color, Integer quantity, Integer cottonPart) {
        this.color = color;
        this.quantity = quantity;
        this.cottonPart = cottonPart;
    }
}
