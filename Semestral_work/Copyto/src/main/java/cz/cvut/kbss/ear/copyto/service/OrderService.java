package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.OrderContainerDao;
import cz.cvut.kbss.ear.copyto.dao.OrderDao;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    private final OrderContainerDao containerDao;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderContainerDao containerDao, OrderDao orderDao){
        this.containerDao = containerDao;
        this.orderDao = orderDao;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders() {
        return orderDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> findOldestOrders() {
        List<Order> toReturn = new ArrayList<>();
        for(Order o : orderDao.findAll()){
            long diffInMillies = Math.abs(new Date().getTime() - o.getInsertionDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff > 60 && o.getState() == OrderState.ADDED){
                toReturn.add(o);
            }
        }
        return toReturn;
    }

    @Transactional(readOnly = true)
    public List<Order> findNewestOrders() {
        List<Order> toReturn = new ArrayList<>();
        for(Order o : orderDao.findAll()){
            long diffInMillies = Math.abs(new Date().getTime() - o.getInsertionDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff < 7 && o.getState() == OrderState.ADDED){
                toReturn.add(o);
            }
        }
        return toReturn;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrdersWithComingDeadline() {
        List<Order> toReturn = new ArrayList<>();
        for(Order o : orderDao.findAll()){
            long diffInMillies = Math.abs(o.getDeadline().getTime() - new Date().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff < 7 && o.getState() == OrderState.ADDED){
                toReturn.add(o);
            }
        }
        return toReturn;
    }

    @Transactional(readOnly = true)
    public List<Order> findLongTermOrders() {
        List<Order> toReturn = new ArrayList<>();
        for(Order o : orderDao.findAll()){
            long diffInMillies = Math.abs(o.getDeadline().getTime() - new Date().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff > 60 && o.getState() == OrderState.ADDED){
                toReturn.add(o);
            }
        }
        return toReturn;
    }



}
