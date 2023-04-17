package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserToEmailValidator extends CustomValidatorBean {
    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
        UserTo userTo = (UserTo) target;
        if (!userTo.getEmail().isEmpty()) {
            User user = userService.getByEmail(userTo.getEmail());
            if (user != null) {
                errors.rejectValue("email", "error.duplicateEmail");
            }
        }
    }
}
