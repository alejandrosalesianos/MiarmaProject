package com.salesianostriana.edu.MiarmaProject.validation.simple;

import com.salesianostriana.edu.MiarmaProject.validation.validator.UniqueNickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy = UniqueNickValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueNick {

    String message() default "Este nick ya existe";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

}
