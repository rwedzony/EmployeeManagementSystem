package com.rafwedz.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EmployeeController {

    @GetMapping("/")
    public String welcomePage(){
        return "index";
    }


}
