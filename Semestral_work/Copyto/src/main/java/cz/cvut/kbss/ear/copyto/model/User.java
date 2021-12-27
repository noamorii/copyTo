package cz.cvut.kbss.ear.copyto.model;

import cz.cvut.kbss.ear.copyto.enums.Role;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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
