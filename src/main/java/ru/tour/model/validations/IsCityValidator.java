package ru.tour.model.validations;


import org.springframework.beans.factory.annotation.Autowired;
import ru.tour.repo.CityRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsCityValidator implements ConstraintValidator<IsCityTaken, String> {
    @Autowired
    private CityRepo cityRepo;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new NullPointerException();
        }
        return this.cityRepo.findByName(value) == null;
    }

}
