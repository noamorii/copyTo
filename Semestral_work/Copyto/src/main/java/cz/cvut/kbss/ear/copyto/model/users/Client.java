package cz.cvut.kbss.ear.copyto.model.users;

import cz.cvut.kbss.ear.copyto.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    public Client() {
        this.role = Role.CLIENT;
    }
}
