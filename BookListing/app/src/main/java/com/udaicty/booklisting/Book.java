package com.udaicty.booklisting;

import java.util.List;

/**
 * Created by win8 on 8/5/2016.
 */
public class Book {

    String tittle;
    List<String> author;

    public Book(String tittle, List<String> author) {
        this.tittle = tittle;
        this.author = author;
    }

    public List<String> getAuthor() {
        return author;
    }

    public String getTittle() {
        return tittle;
    }

}
