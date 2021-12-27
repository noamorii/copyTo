package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CopywriterService extends UserService{

    @Autowired
    public CopywriterService(UserDao userDao){
        super(userDao);
    }

}
