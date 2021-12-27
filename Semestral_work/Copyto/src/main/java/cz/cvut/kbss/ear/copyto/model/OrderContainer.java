package cz.cvut.kbss.ear.copyto.model;

import cz.cvut.kbss.ear.copyto.model.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "C_OrderContainer")
@NamedQueries({
        @NamedQuery(name = "OrderContainer.findByAssignee", query = "SELECT o from OrderContainer o WHERE :assignee = o.assignee"),
        @NamedQuery(name = "OrderContainer.findByClient", query = "SELECT o from OrderContainer o WHERE :client = o.client"),
        @NamedQuery(name = "OrderContainer.findByOrder", query = "SELECT o from OrderContainer o WHERE :order = o.order"),
        @NamedQuery(name = "OrderContainer.findAllCandidates", query = "SELECT o.candidates from OrderContainer o"),
        @NamedQuery(name = "OrderContainer.findAvailableOrders", query = "SELECT o.order FROM OrderContainer o WHERE o.assignee is null" ),
})
public class OrderContainer extends AbstractEntity{

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User assignee = null; // TODO default null

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User client;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Order order;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Workplace workplace;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<User> candidates = new ArrayList<>();

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean open = true;

}


