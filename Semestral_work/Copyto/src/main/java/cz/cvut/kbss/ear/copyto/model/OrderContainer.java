package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import javax.print.attribute.standard.Copies;
import java.util.ArrayList;
import java.util.List;

@Entity
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


