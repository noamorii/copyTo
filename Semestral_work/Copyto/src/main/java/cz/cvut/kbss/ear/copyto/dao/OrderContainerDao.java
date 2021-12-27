package cz.cvut.kbss.ear.copyto.dao;

import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OrderContainerDao extends BaseDao<OrderContainer> {

    protected OrderContainerDao() {
        super(OrderContainer.class);
    }

    public List<OrderContainer> findByAssignee(User assignee){
        Objects.requireNonNull(assignee);
        return em.createNamedQuery("OrderContainer.findByAssignee", OrderContainer.class).setParameter("assignee", assignee)
                .getResultList();
    }

    public List<OrderContainer> findByClient(User client){
        Objects.requireNonNull(client);
        return em.createNamedQuery("OrderContainer.findByClient", OrderContainer.class).setParameter("client", client)
                .getResultList();
    }

    public OrderContainer findByDetail(Order order){
        Objects.requireNonNull(order);
        return em.createNamedQuery("OrderContainer.findByOrder", OrderContainer.class).setParameter("order", order)
                .getSingleResult();
    }

    public List<User> findAllCandidates() {
        return em.createNamedQuery("OrderContainer.findAllCandidates", User.class).getResultList();
    }

    public List<Order> findAvailableOrders() {
        return em.createNamedQuery("OrderContainer.findAvailableOrders", Order.class).getResultList();
    }
}
