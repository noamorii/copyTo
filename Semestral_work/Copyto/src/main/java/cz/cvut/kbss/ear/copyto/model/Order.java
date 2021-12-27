package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
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



}
