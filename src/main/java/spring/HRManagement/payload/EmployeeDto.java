package spring.HRManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @NotNull
    @Length(min = 3,max = 50)
    private String firstName;

    @Size(min = 2,max = 50)
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    @Length(min=8)
    private String password;

    private double salary;

}
