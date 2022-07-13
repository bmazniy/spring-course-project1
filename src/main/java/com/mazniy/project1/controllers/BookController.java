package com.mazniy.project1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mazniy.project1.dao.BookDAO;
import com.mazniy.project1.dao.PersonDAO;
import com.mazniy.project1.models.Book;
import com.mazniy.project1.models.Person;

@Controller
@RequestMapping("/books")
public class BookController {
	
	private final BookDAO bookDAO;
	private final PersonDAO personDAO;

	@Autowired
	public BookController(BookDAO bookDAO, PersonDAO personDAO) {
		super();
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
	}
	
	@GetMapping()
	public String index(Model model) {
		 model.addAttribute("books", bookDAO.index());
		return "books/index";
	}
	
	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
	}
	
	@GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("bookTaken", bookDAO.bookHasTaken(id));
        model.addAttribute("person", personDAO.showByBookId(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("chPerson", new Person());
        return "books/show";
    }
	
	@GetMapping("/{id}/edit")
	public String newBook(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", bookDAO.show(id));
		return "books/edit";
	}
	
	@PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }
	
	@PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }
	
	@DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
	
	@PostMapping("/release/{id}")
    public String release(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult
    		, @PathVariable("id") int id) {
        
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
	
	@PatchMapping("/takeABook/{id}")
    public String takeABook(@ModelAttribute("chPerson") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        
        bookDAO.takeABook(id, person.getId());
        return "redirect:/books/" + id;
    }

}
