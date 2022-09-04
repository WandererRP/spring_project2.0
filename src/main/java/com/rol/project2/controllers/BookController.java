package com.rol.project2.controllers;


import com.rol.project2.models.Book;
import com.rol.project2.models.Person;
import com.rol.project2.services.BooksService;
import com.rol.project2.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;


@Validated
@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;


    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear)); // выдача всех книг
        else {model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));
        if (page >= 0) {
            model.addAttribute("next", (page + 1));
            if (page > 0) {model.addAttribute("previous", (page - 1));
                System.out.println(page);}
            model.addAttribute("booksPerPage", booksPerPage);
            model.addAttribute("sortByYear", sortByYear);

        }

        }

        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("person") Person person) {

        model.addAttribute("book", booksService.findOne(book_id));
        Optional<Person> owner = booksService.getBookOwner(book_id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());

        return "books/new";
    }
    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchResult(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchByBookName(query));
        return "books/search";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", booksService.findOne(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("book_id") int book_id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(book_id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("book_id") int book_id) {
        booksService.assign(book_id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id) {
        booksService.delete(book_id);
        return "redirect:/books";

    }


}
