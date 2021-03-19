package com.registrationPlateform.student.repositories;

import com.registrationPlateform.student.domain.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends CrudRepository<ConfirmationToken,Long> {
    Optional<ConfirmationToken> findByToken(String token);
}
