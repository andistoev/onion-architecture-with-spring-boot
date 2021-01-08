package com.andistoev.onionarchservice.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ShoppingItem {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private String productName;

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Double price;

    @Column(nullable = false)
    @Size(min = 1, max = 1000)
    private int quantity;

    public Double getSubtotalPrice() {
        return price * quantity;
    }
}
