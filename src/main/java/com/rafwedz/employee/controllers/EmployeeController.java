package com.rafwedz.employee.controllers;

import com.rafwedz.employee.models.Employee;
import com.rafwedz.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;


@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/")
    public String welcomePage(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index";
    }

    @GetMapping("/add")
    public String showEmployeeForm(Model model) {
        Employee employee=new Employee();
        model.addAttribute("employee",employee);
        return "forms/employee_form";

    }
    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {

        employeeService.save(employee);

        return "redirect:/";
    }

    @PostMapping(value = "/edit")
    public ModelAndView edit(@RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.getById(emp_id);
        return new ModelAndView("forms/employee_form", "employee", employee);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value="emp_id") int emp_id) {
        Employee employee=employeeService.getById(emp_id);
        employeeService.delete(employee);
        return "redirect:/";
    }


}