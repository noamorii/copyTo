package cz.cvut.kbss.ear.copyto.model.users;

import cz.cvut.kbss.ear.copyto.enums.Role;
import cz.cvut.kbss.ear.copyto.model.AbstractEntity;
import cz.cvut.kbss.ear.copyto.model.Conversation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "C_User")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE :role = u.role"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE :email = u.email"),
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE :firstName = u.firstName and :surname = u.surname")
})
public class User extends AbstractEntity {

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
    protected Role role = Role.USER;

    public Role getRole() {
        return role;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
