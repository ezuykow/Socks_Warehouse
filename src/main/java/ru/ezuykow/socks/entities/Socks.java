package ru.ezuykow.socks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ezuykow.socks.enums.Operation;

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

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Column(name = "cotton_part")
    private Integer cottonPart;
}
