package cz.cvut.kbss.ear.copyto.model.users;

import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@NamedQueries({ // TODO zkontrolovat
        @NamedQuery(name = "Client.findInterestedCopywriters", query = "SELECT o.candidates FROM OrderContainer o WHERE :client = o.client and :order = o" )
})
public class Client extends User {

    @ElementCollection
    private List<String> webs = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "clientId")
    private List<OrderContainer> orders = new ArrayList<>();

    @Basic(optional = false)
    @Column(nullable = true)
    private String residence;

    public Client() {
        this.role = Role.CLIENT;
    }

    public void addOrder(OrderContainer orderContainer) {
        orders.add(orderContainer);
    }

}
