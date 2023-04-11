package spring.HRManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.HRManagement.entity.Employee;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee,UUID> {
    boolean existsByEmail(@Email String email);
    boolean existsByEmailCodeAndEmail(String emailCode, @Email String email);
    Optional<Employee> getEmployeeByEmail(@Email String email);
    boolean existsByEmailAndPassword(@Email String email, String password);

    Optional<Employee> findByEmail(String username);
}
