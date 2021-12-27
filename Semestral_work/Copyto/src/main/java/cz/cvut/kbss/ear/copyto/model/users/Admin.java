package cz.cvut.kbss.ear.copyto.model.users;

import cz.cvut.kbss.ear.copyto.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        this.role = Role.ADMIN;
    }
}
