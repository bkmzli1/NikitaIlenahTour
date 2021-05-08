package ru.tour.model.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsCountryValidator.class)
public @interface IsCountryTaken {
    String message() default "Страна уже внесена";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
