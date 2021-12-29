package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.UserDao;
import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    protected final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder){
        this.dao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> find() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public User find(int id) {
        return dao.find(id);
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
        user.encodePassword(passwordEncoder);
        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }
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
