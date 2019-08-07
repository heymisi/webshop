package kmihaly.mywebshop;


import com.vaadin.flow.component.icon.VaadinIcon;
import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getUserName().length() < 4 || user.getUserName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
              /*  if (daoUserService.findUserByName(user.getUserName()) != null) {
+            errors.rejectValue("username", "Duplicate.userFrom.username");
+        }*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userFrom.password");
        }

    }
}
        /*
        if (user.getUserName().length() < 4 || user.getUserName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }*/
      /*  if (daoUserService.findUserByName(user.getUserName()) != null) {
            errors.rejectValue("username", "Duplicate.userFrom.username");
        }*/
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userFrom.password");
//        }