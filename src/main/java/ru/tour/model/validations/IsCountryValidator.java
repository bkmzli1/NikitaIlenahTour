package ru.tour.model.validations;


import org.springframework.beans.factory.annotation.Autowired;
import ru.tour.repo.CountryRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsCountryValidator implements ConstraintValidator<IsCountryTaken, String> {
    @Autowired
    private CountryRepo countryRepo;


    @Override public boolean isValid(String value, ConstraintValidatorContext context) {

        return this.countryRepo.findByName(value) == null;
    }

}
