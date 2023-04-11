package spring.HRManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.HRManagement.entity.Turniket;
import spring.HRManagement.repository.TurniketRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TurniketService {
    @Autowired
    TurniketRepository turniketRepository;

    public List<Turniket> getTurniketsService(){
        return turniketRepository.findAll();
    }
    public Turniket getTurniketService(UUID id){
        Optional<Turniket> optionalTurniket = turniketRepository.findById(id);
        return optionalTurniket.orElse(null);
    }
}
