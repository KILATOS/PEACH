package org.peach.app.util;

import org.peach.app.models.User;
import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {

    private final UsersRepository usersRepository;

    @Autowired
    public UserValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User curUser = (User) target;
        if (usersRepository.findOneByName(curUser.getName()).isPresent()){
            errors.rejectValue("name","","This is name is already exists");
        }

    }
}
