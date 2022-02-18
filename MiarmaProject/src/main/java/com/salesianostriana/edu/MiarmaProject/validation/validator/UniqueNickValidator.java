package com.salesianostriana.edu.MiarmaProject.validation.validator;

import com.salesianostriana.edu.MiarmaProject.users.repository.UserEntityRepository;
import com.salesianostriana.edu.MiarmaProject.validation.simple.UniqueNick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNickValidator implements ConstraintValidator<UniqueNick,String> {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void initialize(UniqueNick constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nick, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(nick) && !userEntityRepository.existsByNombreUsuario(nick);
    }
}
