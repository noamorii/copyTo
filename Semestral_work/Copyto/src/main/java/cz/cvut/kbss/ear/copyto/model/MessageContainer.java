package cz.cvut.kbss.ear.copyto.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MessageContainer extends AbstractEntity{

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "messageId")
    private List<Message> messages = new ArrayList<>();

    @ManyToMany
    private List<User> users = new ArrayList<>();


}
