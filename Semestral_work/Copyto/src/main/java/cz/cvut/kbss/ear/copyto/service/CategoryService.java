package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.CategoryDao;
import cz.cvut.kbss.ear.copyto.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;
    private final OrderDao orderDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao, OrderDao orderDao){
        this.categoryDao = categoryDao;
        this.orderDao = orderDao;
    }
}
