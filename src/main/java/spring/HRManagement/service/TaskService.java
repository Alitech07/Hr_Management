package spring.HRManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import spring.HRManagement.config.SecurityConfig;
import spring.HRManagement.entity.Employee;
import spring.HRManagement.entity.Task;
import spring.HRManagement.entity.enums.TaskStatusName;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.TaskDto;
import spring.HRManagement.repository.EmployeeRepository;
import spring.HRManagement.repository.TaskRepository;
import spring.HRManagement.repository.TaskStatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    SecurityConfig securityConfig;

    public List<Task> getTasksService(){
        return taskRepository.findAll();
    }

    public Task getTaskService(UUID id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public ApiResponse addTaskService(TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setComment(taskDto.getComment());
        task.setCompletedAt(taskDto.getCompletedAt());
        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployee_id());
        if (!optionalEmployee.isPresent()) return new ApiResponse("This employee not found.",false);
        Employee employee = optionalEmployee.get();
        task.setEmployee(employee);
        task.setTaskStatus(taskStatusRepository.getTaskStatusesByTaskStatusName(TaskStatusName.TASK_STATUS_NEW));

        Task saveTask = taskRepository.save(task);
        sendEmailTask(saveTask.getEmployee().getEmail(),saveTask);

        return new ApiResponse("add Task.",true);

    }

    public ApiResponse editTaskService(TaskDto taskDto,UUID id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("This task not found.",false);
        Task task = optionalTask.get();
        task.setTitle(taskDto.getTitle());
        task.setComment(taskDto.getComment());
        task.setCompletedAt(taskDto.getCompletedAt());
        Optional<Employee> employee = employeeRepository.findById(taskDto.getEmployee_id());
        if (!employee.isPresent()) return new ApiResponse("bundya xodim mavjud emas.",false);
        task.setEmployee(employee.get());
        task.setTaskStatus(taskStatusRepository.getTaskStatusesByTaskStatusName(TaskStatusName.TASK_STATUS_NEW));
        taskRepository.save(task);
        return new ApiResponse("Vaziga yangilandi.",true);
    }

    public ApiResponse deleteTaskService(UUID id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("This employee not found.",false);
        taskRepository.deleteById(id);
        return new ApiResponse("Deleted Employee.",true);
    }

    public boolean sendEmailTask(String email,Task task){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hr_management@gmail.com");
            message.setTo(email);
            message.setSubject(task.getTitle());
            String text = task.getComment()+"\n Bajarilish muddati."+task.getCompletedAt()+"\nStatus: "+task.getTaskStatus();
            message.setText(text);
            securityConfig.javaMailSender().send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
