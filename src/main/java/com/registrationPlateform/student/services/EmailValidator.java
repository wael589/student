package com.registrationPlateform.student.services;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate {
    @Override
    public boolean test(Object o) {
        return true;
    }
}
