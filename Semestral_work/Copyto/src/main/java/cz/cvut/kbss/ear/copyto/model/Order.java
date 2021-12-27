package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import java.sql.Date;
import java.util.List;

@Entity
public class Order extends AbstractEntity{

    @ManyToMany
    @OrderBy("name")
    private List<Category> categories;

    private double price;

    private Date insertionDate;

    private Date deadline;

    private String link;



}
