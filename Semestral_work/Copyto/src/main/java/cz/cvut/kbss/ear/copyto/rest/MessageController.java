package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Conversation;
import cz.cvut.kbss.ear.copyto.model.Message;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.MessageService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/messages")
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    // --------------------CREATE--------------------------------------

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_CLIENT', 'ROLE_COPYWRITER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMessage(@RequestBody Message message) {
        messageService.createMessage(message);
        LOG.debug("Sended message {}.", message);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", message.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_CLIENT', 'ROLE_COPYWRITER')")
    @PostMapping(value = "author-id/{authorId}/ receiver-id/{receiverId}/text/{text}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMessage(@PathVariable Integer authorId, @PathVariable Integer receiverId, @PathVariable String text) {
        Message message = new Message();
        message.setAuthor(userService.find(authorId));
        message.setReceiver(userService.find(receiverId));
        message.setText(text);
        messageService.sendMessage(message);
        LOG.debug("Sended message {}.", message);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", message.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_CLIENT', 'ROLE_COPYWRITER')")
    @PostMapping(value = "author-id/{authorId}/text/{text}/group-message",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendGroupMessage(@RequestBody ArrayList<Integer> userIds, @PathVariable Integer authorId, @PathVariable String text) {
        User author = userService.find(authorId);
        List<User> receivers = new ArrayList<>();
        for(Integer id : userIds){
            receivers.add(userService.find(id));
        }
        messageService.sendGroupMessage(author, receivers, text);
        LOG.debug("Sended group message {}.", text);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getMessage() {
        return messageService.findMessages();
    }

    // TODO opravneni useri
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getById(@PathVariable Integer id) {
        final Message message = messageService.findMessage(id);
        if (message == null) {
            throw NotFoundException.create("Message", id);
        } return message;
    }

    // TODO opravneni useri
    @GetMapping(value = "/id-conversation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getByConversation(@PathVariable Integer id) {
        final Conversation conversation = messageService.findConversation(id);
        final List<Message> messages = messageService.findAllMessagesInConversation(conversation);
        if(conversation == null){
            throw NotFoundException.create("Conversation", id);
        }
        return messages;
    }

    // TODO opravneni ueri
    @GetMapping(value = "/id-author/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getByAuthor(@PathVariable Integer id) {
        final User user = userService.find(id);
        final List<Message> message = messageService.findMessagesByAuthor(user);
        if (message == null) {
            throw NotFoundException.create("Author", id);
        } return message;
    }

    // TODO opravneni useri
    @GetMapping(value = "/id-receiver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getByReceiver(@PathVariable Integer id) {
        final User user = userService.find(id);
        final List<Message> message = messageService.findMessagesByReceiver(user);
        if (message == null) {
            throw NotFoundException.create("Receiver", id);
        } return message;
    }

}
