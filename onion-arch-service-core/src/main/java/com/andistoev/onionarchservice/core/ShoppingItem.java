package com.andistoev.onionarchservice.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ShoppingItem {

    @Id
    private final UUID id = UUID.randomUUID();

    @JoinColumn(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private String productName;

    @JoinColumn(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Double price;

    @JoinColumn(nullable = false)
    private int quantity;

    @Transient
    public Double getSubtotalPrice() {
        return price * quantity;
    }
}
