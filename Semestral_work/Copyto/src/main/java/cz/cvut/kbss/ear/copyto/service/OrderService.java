package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.OrderContainerDao;
import cz.cvut.kbss.ear.copyto.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderContainerDao containerDao;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderContainerDao containerDao, OrderDao orderDao){
        this.containerDao = containerDao;
        this.orderDao = orderDao;
    }

}
