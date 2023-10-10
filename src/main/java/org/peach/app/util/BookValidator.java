package org.peach.app.util;

import org.peach.app.models.Book;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book curBook = (Book) target;
        errors.getFieldErrors("year").forEach(o-> System.out.println(o.getDefaultMessage()));
        if (!errors.getFieldErrors("year").isEmpty()) {
            FieldError error = errors.getFieldErrors("year").stream().filter(o->o.getDefaultMessage().contains("NumberFormatException")).findAny().orElse(null);
            if (error!=null) {
                try {
                    Field defaultMessage = error.getClass().getSuperclass().getSuperclass().getDeclaredField("defaultMessage");
                    defaultMessage.setAccessible(true);
                    defaultMessage.set(error, "Year should be numerical");
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}