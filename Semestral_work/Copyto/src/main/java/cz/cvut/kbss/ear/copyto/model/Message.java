package cz.cvut.kbss.ear.copyto.model;

import cz.cvut.kbss.ear.copyto.model.users.User;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "C_Message")
@NamedQueries({
        @NamedQuery(name = "Message.findAllMessagesByAuthor", query = "SELECT m from Message m WHERE :user = m.author"),
        @NamedQuery(name = "Message.findAllMessagesByReceiver", query = "SELECT m from Message m WHERE :user = m.receiver"),
        @NamedQuery(name = "Message.findAllMessagesByText", query = "SELECT m from Message m WHERE :text = m.text"),
        @NamedQuery(name = "Message.findAllMessagesByDate", query = "SELECT m from Message m WHERE :date = m.date")
})

public class Message extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User author;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private User receiver;

    @Basic(optional = false)
    @Column(nullable = false)
    private String text = "";

    @Basic(optional = false)
    @Column(nullable = false)
    private Date date = new Date();

    public Message(User author, User receiver, String text) {
        this.author = author;
        this.receiver = receiver;
        this.text = text;
    }

    public Message() {
    }
}
