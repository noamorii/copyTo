package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> loginUser(@RequestBody HashMap<String, String> request) throws Exception {
        loginService.loginUser(request.get("email"), request.get("password"));
        LOG.trace("User {} successfully logged in", request.get("email"));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

