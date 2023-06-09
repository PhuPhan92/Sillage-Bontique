package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    @Autowired


    @RequestMapping("")
    public String showHomePage() {

        return "home";
    }

    @RequestMapping("/temp")
    public String showTempPage() {
        return "temp";
    }
}
