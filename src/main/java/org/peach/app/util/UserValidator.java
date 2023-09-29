package org.peach.app.util;

import org.peach.app.models.User;
import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

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
        Optional<User> userToFind = usersRepository.findOneByName(curUser.getName());
        if (userToFind.isPresent()){
            if (userToFind.get().getId() != curUser.getId()) {
                errors.rejectValue("name","","This name is already exists");
            }
        }
        if (curUser.getAddress() ==null){
            errors.rejectValue("address", "", "Address cannot be empty");
        }

    }
}
