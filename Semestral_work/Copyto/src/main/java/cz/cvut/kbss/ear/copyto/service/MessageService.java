package cz.cvut.kbss.ear.copyto.service;

import cz.cvut.kbss.ear.copyto.dao.ConversationDao;
import cz.cvut.kbss.ear.copyto.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ConversationDao conversationDao;
    private final MessageDao messageDao;

    @Autowired
    public MessageService(ConversationDao conversationDao, MessageDao messageDao){
        this.conversationDao = conversationDao;
        this.messageDao = messageDao;
    }
}
