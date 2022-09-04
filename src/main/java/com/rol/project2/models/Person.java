package com.rol.project2.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {


    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "([А-Я][А-Яа-я-]+ [А-Я][А-Яа-я-]+ [А-Я][А-Яа-я-]+)",
            message = "Неправильный формат. Должно быть: Фамилия Имя Отчество (Кириллицей)")
    @Column(name = "fullname")
    private String fullname;


    @Min(value = 1900, message = "Year should be greater than 1900")
    @Max(value = 2030, message = "Year should be real")
    @Column(name = "year_of_birth")
    private int year_of_birth;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }


}
