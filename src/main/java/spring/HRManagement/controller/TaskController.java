package spring.HRManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.HRManagement.entity.Task;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.TaskDto;
import spring.HRManagement.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping
    public HttpEntity<?> getTasks(){
        List<Task> tasks = taskService.getTasksService();
        return ResponseEntity.status(200).body(tasks);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTask(@PathVariable UUID id){
        Task task = taskService.getTaskService(id);
        return ResponseEntity.status(200).body(task);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping("/add")
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTaskService(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> editTask(@RequestBody TaskDto taskDto,@PathVariable UUID id){
        ApiResponse apiResponse = taskService.editTaskService(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.deleteTaskService(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
