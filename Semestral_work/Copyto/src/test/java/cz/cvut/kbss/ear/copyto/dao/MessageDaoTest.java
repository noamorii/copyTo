package cz.cvut.kbss.ear.copyto.dao;

import cz.cvut.kbss.ear.copyto.Copyto;
import cz.cvut.kbss.ear.copyto.environment.TestConfiguration;
import cz.cvut.kbss.ear.copyto.model.Message;

import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackageClasses = Copyto.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class MessageDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ConversationDao conversationDao;

    @Test
    public void findAllMessagesByUser() {
        Message message = Generator.generateMessage();
        em.persist(message);

        User userFrom = message.getAuthor();
        em.persist(userFrom);
        User userTo = message.getReceiver();
        em.persist(userTo);

//        userFrom.sendMessageTo(userTo, message);
//
//        assertEquals(userTo.getMessages().get(0).getText(),
//                messageDao.findAllMessagesByUser(userTo).get(0).getText());
    }

//    @Test
//    public void findAllMessageContainersByUser() {
//        MessageContainer messageContainer = generateMessageContainer();
//        List<User> usersInContainer = messageContainer.getUsers();
//        for (User user : usersInContainer) {
//            assertEquals(1, messageContainerDao.findAllMessageContainersByUser(user).size());
//        }
//    }
//
//    public MessageContainer generateMessageContainer(){
//        final MessageContainer m = new MessageContainer();
//        em.persist(m);
//        ArrayList<User> users = new ArrayList<>();
//        ArrayList<Message> messages = new ArrayList<>();
//
//        for(int i = 0; i < 20; i++) {
//
//            Message message = Generator.generateMessage();
//            em.persist(message);
//            messages.add(message);
//
//            if (!users.contains(message.getReceiver()) && !users.contains(message.getAuthor())) {
//                User author = message.getAuthor();
//                em.persist(author);
//
//                User receiver = message.getReceiver();
//                em.persist(receiver);
//
//                users.add(author);
//                users.add(receiver);
//            }
//        }
//
//        m.setUsers(users);
//        m.setMessages(messages);
//
//        return m;
//    }
}

