package com.registrationPlateform.student.services.token;

import com.registrationPlateform.student.domain.ConfirmationToken;
import com.registrationPlateform.student.repositories.ConfirmationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationService {

    private final ConfirmationRepository confirmationRepository;

    public ConfirmationService(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationRepository.findByToken(token);

    }

    public void setConfirmedAt(String token){
        ConfirmationToken confirmationToken = confirmationRepository.findByToken(token).get();
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationRepository.save(confirmationToken);
    }
}
