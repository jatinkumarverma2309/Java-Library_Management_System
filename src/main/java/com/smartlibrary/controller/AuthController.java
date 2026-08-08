package com.smartlibrary.controller;

import com.smartlibrary.model.Librarian;
import com.smartlibrary.model.Member;
import com.smartlibrary.repository.LibrarianRepository;
import com.smartlibrary.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final LibrarianRepository librarianRepository;
    private final MemberRepository memberRepository;

    public AuthController(LibrarianRepository librarianRepository, MemberRepository memberRepository) {
        this.librarianRepository = librarianRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String role, @RequestParam int id, @RequestParam String password, 
                        HttpSession session, Model model) {
        if ("librarian".equals(role)) {
            Librarian lib = librarianRepository.findByIdAndPassword(id, password);
            if (lib != null) {
                session.setAttribute("libId", lib.getLibId());
                return "redirect:/librarian/dashboard";
            }
        } else if ("member".equals(role)) {
            Member member = memberRepository.findByIdAndPassword(id, password);
            if (member != null) {
                session.setAttribute("memberId", member.getmId());
                return "redirect:/member/dashboard";
            }
        }
        model.addAttribute("error", "Invalid ID or Password");
        return "index";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Member member, Model model) {
        try {
            member.setmId(memberRepository.getNextId());
            memberRepository.insertMember(member);
            model.addAttribute("success", "Registration Successful! Please login.");
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Error registering: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
