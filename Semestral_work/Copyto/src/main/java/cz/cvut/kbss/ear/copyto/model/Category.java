package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity {

    @ManyToMany(mappedBy = "categories")
    private List<Order> orders = new ArrayList<>();

    private String name;



}
