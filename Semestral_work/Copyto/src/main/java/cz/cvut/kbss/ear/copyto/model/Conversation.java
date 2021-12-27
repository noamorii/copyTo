package cz.cvut.kbss.ear.copyto.model;

import cz.cvut.kbss.ear.copyto.model.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "C_Conversation")
@NamedQueries({
        @NamedQuery(name = "Conversation.findAllConversationsByUser", query = "SELECT m from Conversation m WHERE :user MEMBER OF m.users"),
        @NamedQuery(name = "Conversation.findAllMessages", query = "SELECT c.messages from Conversation c"),
})
public class Conversation extends AbstractEntity{

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "messageId")
    private List<Message> messages = new ArrayList<>();

    @ManyToMany
    private List<User> members = new ArrayList<>();


    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return members;
    }

    public void addMember(User user){
        members.add(user);
    }

    public void addMessage(Message message){
        messages.add(message);
    }
}
