package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getAllBooks(Model model){
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "index";
    }

    @PostMapping("/addBook")
    public String addNewBook(Book book){
        Book result = bookService.createBook(book);
        return "redirect:/";
    }

    @RequestMapping({"/edit", "/edit/{id}"})
    public String editBook(Model model, @PathVariable("id") Optional<Long> id){
        Book book = new Book();
        if(id.isPresent()){
            Optional<Book> optionalBook = bookService.findBookById(id.get());
            if(optionalBook.isPresent()) {
                book = optionalBook.get();
            }
        }
        model.addAttribute("book", book);
        return "add-edit-book";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return "redirect:/";
    }
}
