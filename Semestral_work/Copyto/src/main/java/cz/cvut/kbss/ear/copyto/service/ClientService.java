package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.OrderContainerDao;
import cz.cvut.kbss.ear.copyto.dao.OrderDao;
import cz.cvut.kbss.ear.copyto.dao.UserDao;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.model.users.Client;
import cz.cvut.kbss.ear.copyto.model.users.Copywriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService extends UserService{

    private OrderDao orderDao;
    private OrderContainerDao containerDao;

    @Autowired
    public ClientService(UserDao userDao, OrderContainerDao containerDao, OrderDao orderDao){
        super(userDao);
        this.orderDao = orderDao;
        this.containerDao = containerDao;
    }

    //TODO
    @Transactional
    public void createOrder(Client client) {
        if (exists(client.getEmail())) {
            OrderContainer container = new OrderContainer();
            container.setClient(client);
            client.addOrder(container);
            dao.update(client);
            containerDao.update(container);
        }
    }

    @Transactional
    public void addOrderNote(OrderContainer container, Order order){
        container.setOrder(order);
        orderDao.persist(order);
        containerDao.update(container);
    }

    @Transactional
    public void authorizeAssignee(OrderContainer container, Copywriter assignee){
        container.setAssignee(assignee);
        containerDao.update(container);
    }
}
