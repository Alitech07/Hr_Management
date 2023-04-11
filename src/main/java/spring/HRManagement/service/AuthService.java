package spring.HRManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.HRManagement.entity.Employee;
import spring.HRManagement.entity.Roles;
import spring.HRManagement.payload.ApiResponse;
import spring.HRManagement.payload.LoginDto;
import spring.HRManagement.repository.EmployeeRepository;
import spring.HRManagement.security.JwtProvider;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse login(LoginDto loginDto){

        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            Employee employee = (Employee) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), employee.getRoles());
            return new ApiResponse("Success...",true,token);
        }catch (Exception e)
        {
            return new ApiResponse("Username yoki parol xato!",false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> byEmail = employeeRepository.findByEmail(username);
        if (byEmail.isPresent()) return byEmail.get();
        throw new  UsernameNotFoundException(username+" topilmadi.");
    }
}
