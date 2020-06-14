package com.shop.GoodsShop.Model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Book extends Item {
    @NotBlank(message = "Author must be set")
    private String author;

    @NotBlank(message = "Publisher must be set")
    private String publisher;

    @Positive(message = "Pages cannot be negative or zero")
    private int pages;

    @NotBlank(message = "ISBN number must be set")
    private String ISBN;

    private String binding;


    protected Book() {}

    public Book(String name,
                Long count,
                Double weight,
                Double price,
                String description,
                String code,
                Category category,

                String author,
                String publisher,
                int pages,
                String ISBN) {
        super(name, count, weight, price, description, code, category);

        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
        this.ISBN = ISBN;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }
}
