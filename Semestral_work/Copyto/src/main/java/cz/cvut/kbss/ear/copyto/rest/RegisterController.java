package cz.cvut.kbss.ear.copyto.rest;
import cz.cvut.kbss.ear.copyto.exception.UserAlreadyExistException;
import org.slf4j.Logger;
import cz.cvut.kbss.ear.copyto.dto.UserDTO;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.rest.util.GenericResponse;
import cz.cvut.kbss.ear.copyto.service.RegisterService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegisterController {

    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(WorkplaceController.class);

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public GenericResponse registerUserAccount(UserDTO accountDto) {
//        LOG.debug("Registering user account with information: {}", accountDto);
//        userService.registerNewUserAccount(accountDto);
//        return new GenericResponse("success");
//    }

    @PostMapping("/process_register")
    public String processRegister(UserDTO accountDto) {
        LOG.debug("Registering user account with information: {}", accountDto);
        try {
            userService.registerNewUserAccount(accountDto);
        } catch (UserAlreadyExistException ex) {
            LOG.error("User already exists: {}", accountDto);
        }
        return "login";
    }
}
