package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final UserDao userDao;

    @Autowired
    public ClientService(UserDao userDao){
        this.userDao = userDao;
    }

}
