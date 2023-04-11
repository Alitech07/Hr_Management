package spring.HRManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.HRManagement.entity.Employee;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.EmployeeDto;
import spring.HRManagement.payload.PasswordDto;
import spring.HRManagement.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getEmployees(){
        List<Employee> employees = employeeService.getEmployeesService();
        return ResponseEntity.status(200).body(employees);
    }
    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getEmployee(@PathVariable UUID id){
        Employee employee = employeeService.getEmployeeService(id);
        return ResponseEntity.status(employee!=null?200:409).body(employee);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER')")
    @PostMapping("/add")
    public HttpEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = employeeService.addEmployeeService(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER','ROLE_MANAGER','ROLE_EMPLOYEE')")
    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode,@RequestParam String email){
        ApiResponse apiResponse = employeeService.verifyEmailService(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER','ROLE_MANAGER','ROLE_EMPLOYEE')")
    @PostMapping("/varifyEmail")
    public HttpEntity<?> setPassword(@RequestBody PasswordDto passwordDto,
                                     @RequestParam String emailCode,
                                     @RequestParam String email){

        ApiResponse apiResponse = employeeService.setPasswordService(passwordDto, emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR','ROLE_HR_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteEmployee(@PathVariable UUID id){
        ApiResponse apiResponse = employeeService.deleteEmployeeService(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
