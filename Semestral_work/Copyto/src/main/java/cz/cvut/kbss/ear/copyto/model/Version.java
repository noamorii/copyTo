package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Version.findByDate", query = "SELECT v FROM Version v WHERE :date = v.date"),
        @NamedQuery(name = "Version.findByTitle", query = "SELECT v FROM Version v WHERE :title = v.title")
})
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
