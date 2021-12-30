package cz.cvut.kbss.ear.copyto.Controller;

import cz.cvut.kbss.ear.copyto.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/fadgafdlogin")
public class LoginDirector {
    private static final Logger LOG = LoggerFactory.getLogger(cz.cvut.kbss.ear.copyto.rest.LoginController.class);

    private LoginService loginService;

    @Autowired
    public LoginDirector(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String loginUser(@RequestBody HashMap<String, String> request) throws Exception {
        loginService.loginUser(request.get("email"), request.get("password"));
        LOG.trace("User {} successfully logged in", request.get("email"));
        return "login";
    }
}