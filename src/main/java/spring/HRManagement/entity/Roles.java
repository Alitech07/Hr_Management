package spring.HRManagement.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import spring.HRManagement.entity.enums.RoleName;

import javax.persistence.*;

@Data
@Entity
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
