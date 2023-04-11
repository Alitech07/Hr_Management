package spring.HRManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.HRManagement.entity.Turniket;

import java.util.UUID;

public interface TurniketRepository extends JpaRepository<Turniket, UUID> {
}
