package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Workplace.findEditable", query = "SELECT w from Workplace w WHERE :editable = TRUE"),
        @NamedQuery(name = "Workplace.findAllVersions", query = "select w.versions from Workplace w")
})
public class Workplace extends AbstractEntity{

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "versionId")
    List<Version> versions = new ArrayList<>();

    @Basic(optional = false)
    @Column(nullable = false)
    boolean editable = true;
}
