package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    private Date insertionDate;

    @Basic(optional = false)
    @Column(nullable = true) // TODO kontrola
    private Date deadline;

    @Basic(optional = false)
    @Column(nullable = false)
    private String link;



}
