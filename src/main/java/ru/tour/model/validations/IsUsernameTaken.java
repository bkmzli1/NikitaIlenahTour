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
public @interface IsUsernameTaken {
    String message() default "Имя пользователя уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
