package io.khasang.snet.controller;

import io.khasang.snet.model.By;
import io.khasang.snet.model.*;
import io.khasang.snet.model.CreateTable;
import io.khasang.snet.model.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ComponentScan("io.khasang.snet.model.By")
public class AppController {
    @Autowired
    Hello hello;
    @Autowired
    By by;
    @Autowired
    CreateTable createTable;
    @Autowired
    DeleteTable deleteTable;

    @RequestMapping("/")
    public String hello(Model model){
        model.addAttribute("hello", hello.getHelloMessage());
        model.addAttribute("by", by.getByMessage());
        return "hello";
    }

    @RequestMapping("/create")
    public String createTableCompany(Model model){
        model.addAttribute("create", createTable.tableCreation());
        return "create";
    }

<<<<<<< HEAD
    @RequestMapping("/insert")
    public String insertInTableCompany(Model model) {
        model.addAttribute("create", createTable.insert());
        return "create";
    }

    @RequestMapping("/delete")
    public String deleteTableCompany(Model model){
        model.addAttribute("create", deleteTable.delete());
        return "create";
    }

    @RequestMapping("/delete/{companyId}")
    public String deleteRecord(@PathVariable int companyId, Model model) {
        model.addAttribute("delete", deleteTable.deleteRecord(companyId));
        return "deleteID";
    }

    @RequestMapping("/allCompany")
    public String selectAllCompany(Model model) {
        List<Company> componies = createTable.selectAll();
        model.addAttribute("allCompany", componies);
        return "allCompany";

    @RequestMapping("/confidential/page")
    public String secureTable(Model model){
        model.addAttribute("secure", "This is a very secure place");
        return "secure";
    }

    @RequestMapping(value = {"hello/{name}"}, method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("encode");
        modelAndView.addObject("crypt", new BCryptPasswordEncoder().encode(name));
        return modelAndView;
>>>>>>> development
    }
}