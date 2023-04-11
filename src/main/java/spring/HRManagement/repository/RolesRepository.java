package spring.HRManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.HRManagement.entity.Roles;
import spring.HRManagement.entity.enums.RoleName;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles getRolesByRoleName(RoleName roleName);
}
