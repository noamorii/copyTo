package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Version extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private Date date = new Date();

    @Basic(optional = false)
    @Column(nullable = false)
    private String title = "";

    @Basic(optional = false)
    @Column(nullable = false)
    private String text = "";


}
