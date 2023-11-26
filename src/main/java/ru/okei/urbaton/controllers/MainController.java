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
import ru.okei.urbaton.models.News;
import ru.okei.urbaton.models.Person;
import ru.okei.urbaton.models.Schedule;

import java.util.List;


@Controller
public class MainController {
    boolean auth = false; // авторизация
    boolean adminAuth = false; // авторизация админа
    String[] personData; // данные о пользователе
    String[] fio;
    String groupe; // группа ученика
    List<String> news;
    @Autowired
    PersonDAO personDAO;

    // Главная страница
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("auth", auth);
        return "main_new";
    }

    // Личный кабинет
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("name", fio[0] + " " + fio[1]);
        news = personDAO.getNews();
        model.addAttribute("news", news);
        return "UserLkNews";
    }

    @GetMapping("/schedule")
    public String shedule(Model model) {
        if (personData[4].equals("teacher")) {
            model.addAttribute("nums", 3);
        } else {
            model.addAttribute("nums", 1);
        }
        model.addAttribute("name", fio[0] + " " + fio[1]);
        System.out.println(personData[3]);
        List<Schedule> schedule = personDAO.getSchedule(personData[3], personData);
        model.addAttribute("schedule", schedule);
        if (personData[4].equals("teacher")) {
            model.addAttribute("groupe", "всей");
        } else {
            model.addAttribute("groupe", personData[3]);
        }
        return "UserLkTable";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/login")
    public String loginPost(@ModelAttribute Person person,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        auth = personDAO.login(person); // меняем вход на true
        if (!auth) {
            return "registration";
        }
        personData = personDAO.getData(person); // получаем данные о пользователе
        fio = personData[0].split(" "); // ФИО
        groupe = personData[3];

        System.out.println(personData);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin() {
        return "loginAdmin";
    }

    @PostMapping("/admin")
    public String adminLogin(@ModelAttribute("person") Person person) {
        if (personDAO.adminLogin(person.getEmail(), person.getPassword())) {
            adminAuth = true;
            return "redirect:/adminPanel";
        }
        return "loginAdmin";
    }

    @GetMapping("/adminPanel") // панель админа
    public String adminPanel() {
        if (adminAuth) {
            return "adminPanel";
        }
        return "redirect:/";
    }

    @GetMapping("/adminTable") // изменения в расписании
    public String adminTable(Model model) {
        model.addAttribute("teachers", personDAO.getTeachersGroupe());
        return "adminTable";
    }

    @PostMapping("/adminTable")
    public String addSchedule(@ModelAttribute("schedule") Schedule schedule) {
        personDAO.addSchedule(schedule);
        return "redirect:/adminPanel";
    }

    // Добавление преподавателя
    @GetMapping("/addTeacher")
    public String addTeacher(Model model) {
        model.addAttribute("teachers", personDAO.getTeachers());
        return "adminAddTeacher";
    }

    @GetMapping("/regTeacher")
    public String regTeacher() {
        return "regTeacher";
    }

    @PostMapping("/regTeacher")
    public String saveTeacher(@ModelAttribute("person") Person person) {
        personDAO.save(person, "teacher");
        return "redirect:/addTeacher";
    }

    @GetMapping("/regStudent")
    public String regStudent() {
        return "addStudent";
    }

    @PostMapping("/regStudent")
    public String registrationPost(@ModelAttribute("person") @Valid Person person,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        personDAO.save(person, "student");
        return "redirect:/adminPanel";
    }

    @GetMapping("/addNews")
    public String addNews() {
        return "addNews";
    }

    @PostMapping("/addNews")
    public String addNewsPost(@ModelAttribute("news")News news) {
        personDAO.addNews(news);
        return "redirect:/adminPanel";
    }
}
