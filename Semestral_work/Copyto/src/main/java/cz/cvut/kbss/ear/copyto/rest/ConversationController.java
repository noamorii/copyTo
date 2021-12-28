package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.service.MessageService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ConversationController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ConversationController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    // --------------------CREATE--------------------------------------

    // --------------------READ--------------------------------------

    // --------------------UPDATE--------------------------------------

    // --------------------DELETE--------------------------------------
}
