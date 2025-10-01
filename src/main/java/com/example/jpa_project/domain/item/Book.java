package com.example.jpa_project.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@DiscriminatorValue(value = "book")
public class Book extends Item {

    private String author;
    private String isbn;

    public void change(String name, int price, int stockQuantity, String author, String isbn) {
        super.change(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
