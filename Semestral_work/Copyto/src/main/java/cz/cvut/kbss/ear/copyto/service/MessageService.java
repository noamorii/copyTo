package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.ConversationDao;
import cz.cvut.kbss.ear.copyto.dao.MessageDao;
import cz.cvut.kbss.ear.copyto.model.Conversation;
import cz.cvut.kbss.ear.copyto.model.Message;
import cz.cvut.kbss.ear.copyto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final ConversationDao conversationDao;
    private final MessageDao messageDao;

    @Autowired
    public MessageService(ConversationDao conversationDao, MessageDao messageDao){
        this.conversationDao = conversationDao;
        this.messageDao = messageDao;
    }

    @Transactional(readOnly = true)
    public List<Conversation> findConversations(){
        return conversationDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Conversation> findConversations(User user){
        return conversationDao.findAllConversationsByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Message> findConversation(){
        return conversationDao.findAllMessagesInContainer();
    }

    @Transactional(readOnly = true)
    public Conversation findConversation(int id){
        return conversationDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Message> findMessages(){
        return messageDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Message> findMessages(Date date){
        return messageDao.findAllMessagesByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Message> findMessagesByAuthor(User user){
        return messageDao.findAllMessagesByAuthor(user);
    }

    @Transactional(readOnly = true)
    public List<Message> findMessagesByReceiver(User user){
        return messageDao.findAllMessagesByReceiver(user);
    }

    @Transactional(readOnly = true)
    public Message findMessage(int id){
        return messageDao.find(id);
    }

    @Transactional(readOnly = true)
    public void createConversation(Conversation conversation){
        conversationDao.persist(conversation);
    }

    @Transactional(readOnly = true)
    public void createMessage(Message message){
        messageDao.persist(message);
    }

    // TODO sendMessage
    @Transactional
    public void sendMessage(User author, User receiver, String text){
        Message message = new Message(author, receiver, text);
        messageDao.persist(message);

        List<Conversation> authorsConversation = findConversations(author);
        Conversation toUpdate = null;

        // pokud existuje konverzace mezi temito 2 uzivateli
        for(Conversation c : authorsConversation){
            if(c.getUsers().contains(receiver) && c.getUsers().size() == 2){
                toUpdate = c;
                break;
            }
        }

        if(toUpdate == null){
            toUpdate = new
        }





    }

    // TODO send group message
}



