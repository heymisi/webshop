package kmihaly.mywebshop;


import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.service.DAOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

//    @Autowired
//    DAOUserService daoUserService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getUserName().length() < 4 || user.getUserName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
      /*  if (daoUserService.findUserByName(user.getUserName()) != null) {
            errors.rejectValue("username", "Duplicate.userFrom.username");
        }*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userFrom.password");
        }
    }
}
