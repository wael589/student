package com.registrationPlateform.student.services;

import com.registrationPlateform.student.domain.AppUser;
import com.registrationPlateform.student.domain.ConfirmationToken;
import com.registrationPlateform.student.services.token.ConfirmationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.registrationPlateform.student.repositories.AppUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {
    private final static  String USER_NOT_FOUND = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationService confirmationService;

    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationService confirmationService) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationService = confirmationService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public String signUpUser(AppUser user){
        boolean userExist = appUserRepository.findByEmail(user.getEmail())
                .isPresent();
        if(userExist){
            throw new IllegalStateException("email already taken");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = appUserRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableAppUser(String email){
        AppUser user = appUserRepository.findByEmail(email).get();
        user.setEnabled(true);
    }
}
