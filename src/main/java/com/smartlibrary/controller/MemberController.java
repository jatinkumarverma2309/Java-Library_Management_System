package com.smartlibrary.controller;

import com.smartlibrary.repository.BookRepository;
import com.smartlibrary.repository.LibraryService;
import com.smartlibrary.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final BookRepository bookRepository;
    private final LibraryService libraryService;
    private final MemberRepository memberRepository;

    public MemberController(BookRepository bookRepository, LibraryService libraryService, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("memberId") == null) return "redirect:/";
        return "member_dashboard";
    }

    @GetMapping("/searchBook")
    public String searchBook(@RequestParam(required = false) String bName, HttpSession session, Model model) {
        if (session.getAttribute("memberId") == null) return "redirect:/";
        if (bName != null && !bName.isEmpty()) {
            model.addAttribute("books", bookRepository.searchBooks(bName));
        }
        return "member_search";
    }
    
    @GetMapping("/searchAuthor")
    public String searchAuthor(@RequestParam(required = false) String aName, HttpSession session, Model model) {
        if (session.getAttribute("memberId") == null) return "redirect:/";
        if (aName != null && !aName.isEmpty()) {
            model.addAttribute("authors", libraryService.searchAuthor(aName));
        }
        return "member_search";
    }

    @GetMapping("/searchPublisher")
    public String searchPublisher(@RequestParam(required = false) String pName, HttpSession session, Model model) {
        if (session.getAttribute("memberId") == null) return "redirect:/";
        if (pName != null && !pName.isEmpty()) {
            model.addAttribute("publishers", libraryService.searchPublisher(pName));
        }
        return "member_search";
    }

    @GetMapping("/borrows")
    public String viewBorrows(HttpSession session, Model model) {
        Integer mId = (Integer) session.getAttribute("memberId");
        if (mId == null) return "redirect:/";
        memberRepository.calculatePenalty(mId);
        model.addAttribute("borrows", libraryService.getBorrows(mId));
        return "member_borrows";
    }

    @PostMapping("/issueBook")
    public String issueBook(@RequestParam int bId, HttpSession session, Model model) {
        Integer mId = (Integer) session.getAttribute("memberId");
        if (mId == null) return "redirect:/";
        try {
            int inBorrows = libraryService.checkUserInBorrows(mId);
            if (inBorrows > 0) {
                model.addAttribute("error", "You already have a book issued! Return it first.");
            } else {
                libraryService.issueBook(bId, mId);
                model.addAttribute("success", "Book issued successfully!");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error issuing book (does it exist?).");
        }
        return "member_dashboard";
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam int bId, HttpSession session, Model model) {
        Integer mId = (Integer) session.getAttribute("memberId");
        if (mId == null) return "redirect:/";
        try {
            libraryService.returnBook(bId, mId);
            model.addAttribute("success", "Book returned successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error returning book.");
        }
        return "redirect:/member/borrows";
    }
}
