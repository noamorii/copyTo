package cz.cvut.kbss.ear.copyto.model;

import cz.cvut.kbss.ear.copyto.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE :role = u.role"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE :email = u.email"),
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE :firstName = u.firstName and :surname = u.surname")
})
public class User extends AbstractEntity{

    @ManyToMany
    private List<Conversation> conversations = new ArrayList<>();

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String surname;

    // TODO password like a string?
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private String mobile;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date dateOfBirth;

    @Basic(optional = false)
    @Column(nullable = false)
    private Role role = Role.USER;
}
