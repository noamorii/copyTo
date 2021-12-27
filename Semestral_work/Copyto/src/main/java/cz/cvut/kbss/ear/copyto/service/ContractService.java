package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.ContractDao;
import cz.cvut.kbss.ear.copyto.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final UserDao userDao;
    private final ContractDao contractDao;

    @Autowired
    public ContractService(UserDao userDao, ContractDao contractDao){
        this.userDao = userDao;
        this.contractDao = contractDao;
    }
}
