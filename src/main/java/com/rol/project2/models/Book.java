package com.rol.project2.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;


    @NotEmpty(message = "Book name should not be empty")
    @Column(name = "book_name")
    private String bookName;

    @NotEmpty(message = "Author should not be empty")
    @Column(name = "author")
    private String author;

    @Column(name = "year")
    @Max(value = 2030,message = "Year should not be empty")
    private int year;
    @Column(name="date_of_assignment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Transient
    private boolean expired;

    public boolean isExpired() {
        System.out.println(expired);
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int id) {
        this.book_id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String book_name) {
        this.bookName = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}

