package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.Conversation;
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

public class ConversationController {

    private static final Logger LOG = LoggerFactory.getLogger(ConversationController.class);

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ConversationController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    // --------------------CREATE--------------------------------------

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createConversation(@RequestBody Conversation conversation) {
        messageService.createConversation(conversation);
        LOG.debug("Created conversation {}.", conversation);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", conversation.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Conversation> getConversation() {
        return messageService.findConversations();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Conversation getById(@PathVariable Integer id) {
        final Conversation conversation = messageService.findConversation(id);
        if (conversation == null) {
            throw NotFoundException.create("Conversation", id);
        } return conversation;
    }

    // TODO by user


}
