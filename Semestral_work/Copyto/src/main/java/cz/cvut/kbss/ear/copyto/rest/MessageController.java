package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Conversation;
import cz.cvut.kbss.ear.copyto.model.Message;
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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMessage(@RequestBody Message message) {
        messageService.createMessage(message);
        LOG.debug("Sended message {}.", message);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", message.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getMessage() {
        return messageService.findMessages();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getById(@PathVariable Integer id) {
        final Message message = messageService.findMessage(id);
        if (message == null) {
            throw NotFoundException.create("Message", id);
        } return message;
    }

    // TODO by reciever

    // TODO by sender

}
