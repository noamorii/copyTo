package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Message extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User author;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User receiver;

    @Basic(optional = false)
    @Column(nullable = false)
    private String text = "";

    @Basic(optional = false)
    @Column(nullable = false)
    private Date date = new Date();


}
