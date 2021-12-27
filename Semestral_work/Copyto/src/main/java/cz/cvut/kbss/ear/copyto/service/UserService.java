package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.UserDao;
import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    protected final UserDao dao;

    @Autowired
    public UserService(UserDao userDao){
        this.dao = userDao;
    }

    @Transactional(readOnly = true)
    public List<User> find() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(Role role) {
        return dao.findByRole(role);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findByName(String firstname, String surname) {
        return dao.findByName(firstname, surname);
    }

    //todo
    @Transactional
    public void createAccount(User user) {
        Objects.requireNonNull(user);
        dao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String email) {
        return dao.findByEmail(email) != null;
    }

    @Transactional(readOnly = true)
    public void update(User user) {
        dao.update(user);
    }

}
