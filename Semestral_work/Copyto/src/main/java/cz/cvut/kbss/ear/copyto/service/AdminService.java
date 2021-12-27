package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.CategoryDao;
import cz.cvut.kbss.ear.copyto.dao.UserDao;
import cz.cvut.kbss.ear.copyto.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdminService extends UserService {

    CategoryDao categoryDao;

    @Autowired
    public AdminService(UserDao userDao, CategoryDao categoryDao){
        super(userDao);
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
}
