package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CopywriterService {

    private final UserDao userDao;

    @Autowired
    public CopywriterService(UserDao userDao){
        this.userDao = userDao;
    }


}
