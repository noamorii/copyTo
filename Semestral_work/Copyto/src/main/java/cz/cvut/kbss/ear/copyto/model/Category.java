package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE :name = c.name")
})
public class Category extends AbstractEntity {

    @ManyToMany(mappedBy = "categories")
    private List<Order> orders = new ArrayList<>();

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }
}
