package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.CategoryDao;
import cz.cvut.kbss.ear.copyto.dao.UserDao;
import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdminService extends UserService {

    CategoryDao categoryDao;

    @Autowired
    public AdminService(UserDao userDao, PasswordEncoder passwordEncoder, CategoryDao categoryDao){
        super(userDao, passwordEncoder);
        this.categoryDao = categoryDao;
    }

    // TODO admin
    @Transactional
    public void deleteCategory(Category category) {
        Objects.requireNonNull(category);
        categoryDao.remove(category);
    }

    // TODO Admin
    @Transactional
    public void createCategory(Category category){
        Objects.requireNonNull(category);
        categoryDao.persist(category);
    }

    //TODO Admin
    @Transactional
    public void deleteUser(User user) {
        dao.remove(user);
    }

    @Transactional
    public void blockUser(User toBlock){
        toBlock.setRole(Role.USER);
        dao.update(toBlock);
    }

    @Transactional
    public void unblockClient(User toUnblock){
        toUnblock.setRole(Role.CLIENT);
        dao.update(toUnblock);
    }

    @Transactional
    public void unblockCopywriter(User toUnblock){
        toUnblock.setRole(Role.COPYWRITER);
        dao.update(toUnblock);
    }


}
