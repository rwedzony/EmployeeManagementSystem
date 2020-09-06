package com.rafwedz.employee.controllers;


import com.rafwedz.employee.models.Employee;
import com.rafwedz.employee.models.Task;
import com.rafwedz.employee.services.EmployeeService;
import com.rafwedz.employee.services.TaskService;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public List<Employee> employeeList() {
        List<Employee> employees =new ArrayList<>();
        employees=employeeService.getAllEmployees();
        return employees;
    }


    @GetMapping("/{emp_id}")
    public Employee getEmployeeById(@PathVariable(value="emp_id") String emp_id){
        return employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
    }


    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        System.out.println("create function");
        employeeService.save(employee);
    }

    @PutMapping(value = "/{emp_id}")
    public void update(@PathVariable(value = "emp_id") String emp_id, @RequestBody Employee employee) {
        Employee empTemp = employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
        empTemp.setFirstName(employee.getFirstName());
        empTemp.setLastName(employee.getLastName());
        empTemp.setEmail(employee.getEmail());
        empTemp.setOccupation(employee.getOccupation());
        empTemp.setCurrentMonthWorkingHours(employee.getCurrentMonthWorkingHours());
        employeeService.save(empTemp);

    }


    @DeleteMapping(value="/{emp_id}")
    public void delete(@PathVariable(value="emp_id") String emp_id) {
        List<Task> tasks = taskService.getEmployeeTask(Long.parseLong(emp_id)).orElse(new ArrayList<>());
        tasks.forEach(t->{t.setEmployee(null);
        taskService.save(t);});
        employeeService.deleteEmployeeById(Long.parseLong(emp_id));
    }

    @GetMapping("/{emp_id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable(value="emp_id") String emp_id){
        Employee employee=employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
        List<Task> tasks = taskService.getEmployeeTask(Long.parseLong(emp_id)).orElse(new ArrayList<>());

        return tasks;
    }

    @GetMapping("/{emp_id}/tasks/done")
    public int getEmployeeTasksDone(@PathVariable(value="emp_id") String emp_id){
        Employee employee=employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
        int no_of_tasks = taskService.getEmployeeTaskDone(Long.parseLong(emp_id));
        return no_of_tasks;
    }

    @GetMapping("/{emp_id}/tasks/new")
    public int getEmployeeTasksNew(@PathVariable(value="emp_id") String emp_id){
        Employee employee=employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
        int no_of_tasks = taskService.getEmployeeTaskNew(Long.parseLong(emp_id));
        return no_of_tasks;
    }

    @PatchMapping("/{emp_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Employee partial updated!")
    public void updatePatchEmployee(@RequestBody Map<String, String> updates, @PathVariable(value="emp_id") String emp_id) {
        System.out.println("In the patch function");
        Employee empTemp = employeeService.getEmployeeById(Long.parseLong(emp_id)).orElseThrow(EntityExistsException::new);
        empTemp.setFirstName(updates.get("firstName"));
        empTemp.setLastName(updates.get("lastName"));
        empTemp.setEmail(updates.get("email"));
        if (!updates.get("password").isEmpty()) {
            empTemp.setPassword(passwordEncoder.encode(updates.get("password")));
        }
        this.employeeService.save(empTemp);
    }
}
