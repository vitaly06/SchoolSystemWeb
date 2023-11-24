package ru.okei.urbaton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.okei.urbaton.dao.PersonDAO;
import ru.okei.urbaton.models.Person;

@Controller
public class MainController {
    boolean auth = false; // авторизация
    String[] personData;
    @Autowired
    PersonDAO personDAO;

    // Главная страница
    @GetMapping("/")
    public String index() {
        return "main";
    }
    // Личный кабинет
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("person") Person person){
        auth = personDAO.save(person);
        return "main";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute Person person){
        auth = personDAO.login(person);
        personData = personDAO.getData(person);
        return "main";
    }
}
