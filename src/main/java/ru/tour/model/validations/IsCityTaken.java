package ru.tour.model.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsCityValidator.class)
public @interface IsCityTaken {
    String message() default "Город уже внесен";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
