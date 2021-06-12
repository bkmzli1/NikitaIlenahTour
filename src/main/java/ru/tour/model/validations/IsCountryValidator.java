package ru.tour.model.validations;


import org.springframework.beans.factory.annotation.Autowired;
import ru.tour.domain.Country;
import ru.tour.repo.CountryRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class IsCountryValidator implements ConstraintValidator<IsCountryTaken, String> {
    @Autowired
    private CountryRepo countryRepo;


    @Override public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Country> byName = this.countryRepo.findByName(value);
        return this.countryRepo.findByName(value).size() <=0;
    }

}
