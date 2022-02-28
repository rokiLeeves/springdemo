package com.myself.springdemo.controller;


import com.myself.springdemo.bean.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@NoArgsConstructor
@Controller
@RequestMapping("/view")
public class ModelandViewController {


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model) {
        if ("aa".equals(user.getName())) {
            session.setAttribute("user", user.getId());
            model.addAttribute("msg", "ok");
            return "redirect:/index";
        } else {
            model.addAttribute("msg", "username error");
            return "login";
        }
    }

    @GetMapping("index")
    public String mainIndex(HttpSession session, Model model) {
        if ((session.getAttribute("user") == null)) {
            session.setAttribute("msg", "login");
            model.addAttribute("msg", "main");
            session.setAttribute("msg2", "login2");
            return "main";
        } else {
            return "login";
        }
    }
}
