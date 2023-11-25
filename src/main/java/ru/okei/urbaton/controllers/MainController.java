package ru.okei.urbaton.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.okei.urbaton.dao.PersonDAO;
import ru.okei.urbaton.models.Person;


@Controller
public class MainController {
    boolean auth = false; // авторизация
    String[] personData; // данные о пользователе
    String[] fio;
    @Autowired
    PersonDAO personDAO;

    // Главная страница
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("auth", auth);
        return "main";
    }

    // Личный кабинет
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("name", fio[0] + " " + fio[1]);
        return "UserLkNews";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("person") @Valid Person person,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "registration";
        }
        personDAO.save(person);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute Person person,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "registration";
        }
        auth = personDAO.login(person); // меняем вход на true
        personData = personDAO.getData(person); // получаем данные о пользователе
        fio = personData[0].split(" "); // ФИО
        System.out.println(personData);
        return "redirect:/";
    }
    @GetMapping("/new")
    public String nw() {
        return "new";
    }
    @PostMapping("/new")
    public String newPost(@ModelAttribute Person person,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "new";
        }
        System.out.println("ia");
        return "redirect:/";
    }
}
