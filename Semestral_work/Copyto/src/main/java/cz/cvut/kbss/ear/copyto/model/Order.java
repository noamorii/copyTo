package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Order.findAllByCategory", query = "SELECT o from Order o WHERE :category MEMBER OF o.categories"),
        @NamedQuery(name = "Order.findAllByDeadline", query = "SELECT o from Order o WHERE :deadline > o.deadline"),
        @NamedQuery(name = "Order.findAllByInsertionDate", query = "SELECT o from Order o WHERE :insertionDate = o.insertionDate"),
        @NamedQuery(name = "Order.findByLink", query = "SELECT o from Order o WHERE :link = o.link"),
        @NamedQuery(name = "Order.findByPrice", query = "SELECT o from Order o WHERE :price = o.price")
})
public class Order extends AbstractEntity{

    @ManyToMany
    @OrderBy("name")
    private List<Category> categories;

    @Basic(optional = false)
    @Column(nullable = true) // TODO kontrola
    private double price;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date insertionDate = new Date();

    @Basic(optional = false)
    @Column(nullable = true) // TODO kontrola
    private Date deadline;

    @Basic(optional = false)
    @Column(nullable = false)
    private String link;

    public void setCategory(Category category){
        categories.add(category);
    }

    public void removeCategory(Category category){
        categories.remove(category);
    }
}
