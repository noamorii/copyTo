package cz.cvut.kbss.ear.copyto.dao;

import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends BaseDao<User> {

    protected UserDao() {
        super(User.class);
    }

    public List<User> findByRole(Role role) {
        return em.createNamedQuery("User.findByRole", User.class).setParameter("role", role)
                .getResultList();
    }

    public User findByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email)
                .getSingleResult();
    }

    public User findByName(String firstname, String surname) {
        return em.createNamedQuery("User.findByName", User.class).setParameter("firstName", firstname).setParameter("surname", surname)
                .getSingleResult();
    }

}
