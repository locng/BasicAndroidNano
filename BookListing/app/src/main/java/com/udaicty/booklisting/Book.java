package com.udaicty.booklisting;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by win8 on 8/5/2016.
 */
public class Book {

    String tittle;
    String author;
    Bitmap cover;
    String description;
    public Book(String tittle, String author, Bitmap cover, String description) {
        this.tittle = tittle;
        this.author = author;
        this.cover = cover;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getTittle() {
        return tittle;
    }

    public Book(String tittle, String author) {

        this.tittle = tittle;
        this.author = author;
    }
}
