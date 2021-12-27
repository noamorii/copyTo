package cz.cvut.kbss.ear.copyto.model.users;

import cz.cvut.kbss.ear.copyto.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COPYWRITER")
public class Copywriter extends User {

    public Copywriter() {
        this.role = Role.COPYWRITER;
    }
}
