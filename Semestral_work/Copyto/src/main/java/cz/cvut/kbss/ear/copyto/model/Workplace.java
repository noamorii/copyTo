package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "C_Workplace")
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

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void addVersion(Version version){
        versions.add(version);
    }

    public void removeVersion(Version version){
        versions.remove(version);
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Version getLastVersion(){
        if (versions.size() != 0) return versions.get(versions.size()-1);
        return null;
    }
}
