package spring.HRManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.HRManagement.config.SecurityConfig;
import spring.HRManagement.entity.Employee;
import spring.HRManagement.entity.enums.RoleName;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.EmployeeDto;
import spring.HRManagement.payload.PasswordDto;
import spring.HRManagement.repository.EmployeeRepository;
import spring.HRManagement.repository.RolesRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    SecurityConfig securityConfig;


    /**
     * BARCHA XODIMLARNI OLISH.
     * @return
     */
    public List<Employee> getEmployeesService(){
        return employeeRepository.findAll();
    }

    /**
     * XODIMNI ID BO'YICHA OLISH. BITTA XODIMNI SO'RALADI.
     * @param id
     * @return
     */
    public Employee getEmployeeService(UUID id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    /**
     * YANGI XODIM QO'SHISH.
     * @param employeeDto
     * @return
     */
    public ApiResponse addEmployeeService(EmployeeDto employeeDto){
        boolean exists = employeeRepository.existsByEmail(employeeDto.getEmail());
        if (exists) return new ApiResponse("This email is already registered.",false);
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setRoles(Collections.singleton(rolesRepository.getRolesByRoleName(RoleName.ROLE_EMPLOYEE)));
        employee.setSalary(employeeDto.getSalary());

        String emailCode = UUID.randomUUID().toString();
        employee.setEmailCode(emailCode);
        Employee save = employeeRepository.save(employee);
        sendEmailMessage(save.getEmail(),save.getEmailCode());
        return new ApiResponse("Employee added.",true);
    }

    /**
     * Xodimni id bo'yicha o'chirish.
     * @param id
     * @return
     */
    public ApiResponse deleteEmployeeService(UUID id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) return new ApiResponse("This employee not found.",false);
        employeeRepository.deleteById(id);
        return new ApiResponse("Deleted employee.",true);
    }

    public ApiResponse verifyEmailService(String emailCode, String email){
        boolean exists = employeeRepository.existsByEmailCodeAndEmail(emailCode, email);
        if (!exists) return new ApiResponse("Email tasdiqlanmadi.",false);
        Optional<Employee> optionalEmployee = employeeRepository.getEmployeeByEmail(email);
        if (!optionalEmployee.isPresent())return new ApiResponse("Bunday xodim mavjud emas.",false);
        Employee employee = optionalEmployee.get();
        employee.setEnabled(true);
        return new ApiResponse("Email tasdiqlandi.",true);
    }

    /**
     * Emailda parol o'rnatish.
     * @param passwordDto
     * @param emailCode
     * @param email
     * @return
     */
    public ApiResponse setPasswordService(PasswordDto passwordDto,String emailCode,String email){
        if (passwordDto.getPassword().equals(passwordDto.getConfirmPassword())){
            Optional<Employee> employeeByEmail = employeeRepository.getEmployeeByEmail(email);
            if (!employeeByEmail.isPresent()) return new ApiResponse("Bumday xodim mavjud emas",false);
            Employee employee = employeeByEmail.get();
            employee.setPassword(passwordDto.getPassword());
            employeeRepository.save(employee);
            return new ApiResponse("Parol o'rnatildi.",true);
        }
        return new ApiResponse("Parol xato kiritildi.",false);
    }

    /**
     * EMAIL MANZILIGA TASDIQLASH CODINI YUBORISH.
     * @param email
     * @param emailCode
     * @return
     */
    public boolean sendEmailMessage(String email,String emailCode){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hr_management@gmail.com");
            message.setTo(email);
            message.setSubject("Verification code.");
            message.setText("<a href='http://localhost:7171/api/auth/verifyEmail?Code="+emailCode+"&email="+email+"'>Tasdiqlash</a>");
            securityConfig.javaMailSender().send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
