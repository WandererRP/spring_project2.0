package com.rol.project2.services;

import com.rol.project2.models.Book;
import com.rol.project2.models.Person;
import com.rol.project2.repositories.BooksRepository;
import com.rol.project2.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }


    public Book findOne(int id) {
        Optional<Book> foundPerson = booksRepository.findById(id);
        return foundPerson.orElse(null);
    }


    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBook_id(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int book_id) {
        return booksRepository.findById(book_id).map(Book::getOwner);
    }

    @Transactional
    public void assign(int book_id, Person person) {
        booksRepository.findById(book_id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setDate(new Date());
                });
    }

    @Transactional
    public void release(int book_id) {
        booksRepository.findById(book_id).ifPresent(book -> {
            book.setOwner(null);
            book.setDate(null);
        });
    }


    public List<Book> findByOwner(Person owner) {
        List<Book> books = booksRepository.findByOwner(owner);
        if(!books.isEmpty()){
            books.forEach(book -> {
                long millis = Math.abs(book.getDate().getTime() - new Date().getTime());
                if (millis > 864000000)
                    book.setExpired(true);
            });
        }
        return books;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {

        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
    }

    public List<Book> searchByBookName(String query) {
        return booksRepository.findByBookNameStartingWith(query);
    }
}
