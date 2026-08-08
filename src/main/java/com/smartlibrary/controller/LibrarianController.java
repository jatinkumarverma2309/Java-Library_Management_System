package com.smartlibrary.controller;

import com.smartlibrary.model.Book;
import com.smartlibrary.repository.BookRepository;
import com.smartlibrary.repository.LibraryService;
import com.smartlibrary.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final BookRepository bookRepository;
    private final LibraryService libraryService;
    private final MemberRepository memberRepository;

    public LibrarianController(BookRepository bookRepository, LibraryService libraryService, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        return "librarian_dashboard";
    }

    @PostMapping("/addBook")
    public String addBook(Book book, HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        try {
            bookRepository.insertBook(book);
            model.addAttribute("success", "Book added successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error adding book.");
        }
        return "librarian_dashboard";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestParam int aId, @RequestParam String aName, @RequestParam String aEmail, 
                            HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        libraryService.addAuthor(aId, aName, aEmail);
        model.addAttribute("success", "Author added successfully!");
        return "librarian_dashboard";
    }

    @PostMapping("/addPublisher")
    public String addPublisher(@RequestParam int pId, @RequestParam String pName, @RequestParam String pEmail, 
                               HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        libraryService.addPublisher(pId, pName, pEmail);
        model.addAttribute("success", "Publisher added successfully!");
        return "librarian_dashboard";
    }

    @GetMapping("/books")
    public String viewBooks(HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        model.addAttribute("books", bookRepository.findAllBooks());
        return "librarian_books";
    }

    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam int bId, HttpSession session) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        bookRepository.deleteBook(bId);
        return "redirect:/librarian/books";
    }

    @PostMapping("/updateAisle")
    public String updateAisle(@RequestParam int bId, @RequestParam int aisle, HttpSession session) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        bookRepository.updateAisle(bId, aisle);
        return "redirect:/librarian/books";
    }

    @GetMapping("/members")
    public String viewMembers(HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        model.addAttribute("members", memberRepository.findAll());
        return "librarian_members";
    }

    @PostMapping("/resetPenalty")
    public String resetPenalty(@RequestParam int mId, HttpSession session) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        memberRepository.resetPenalty(mId);
        return "redirect:/librarian/members";
    }

    @GetMapping("/history")
    public String viewHistory(HttpSession session, Model model) {
        if (session.getAttribute("libId") == null) return "redirect:/";
        model.addAttribute("history", libraryService.getHistory(0));
        return "librarian_history";
    }
}
