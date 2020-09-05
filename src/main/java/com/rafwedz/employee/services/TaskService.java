package com.rafwedz.employee.services;

import com.rafwedz.employee.dto.TaskDto;
import com.rafwedz.employee.models.Employee;
import com.rafwedz.employee.models.Task;
import com.rafwedz.employee.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private TaskRepository employeeRepository;

    public List<Task> getAllTask(){

        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Task> getAllUnassignedTasks(){
        return taskRepository.findAllUnassignedTasks();
    }
    public List<Task> getAllassignedTasks(){
        return taskRepository.findAllassignedTasks();
    }

    public Optional<Task> getTaskById(Long task_id){
        return taskRepository.findById(task_id);
    }

    public Optional<List<Task>> getEmployeeTask(Long id){
        return taskRepository.findEmployeeTask(id);
    };
    public void save(final Task task){taskRepository.save(task);
    }

    public List<TaskDto> getTasksDtos() {
        List<TaskDto> TaskDto=new ArrayList<>();
        List<Task> AssignedTask=taskRepository.findAllassignedTasks();
        AssignedTask.forEach(t->TaskDto.add(new TaskDto(t.getId(),
                t.getDescription(),
                t.getStatus(),
                t.getStartDate(),
                t.getEndDate(),
                t.getEmployee().getFirstName(),
                t.getEmployee().getLastName())));

        return TaskDto;
    }
}