package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.service.ClientService;
import cz.cvut.kbss.ear.copyto.service.OrderService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/client")
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ClientController(ClientService clientService, UserService userService, OrderService orderService){
        this.clientService = clientService;
        this.userService = userService;
        this.orderService = orderService;
    }

    // TODO opravneny client
    @PutMapping(value = "/id-assignee/{assiggneId}/id-container/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authorizeAssignee(@PathVariable Integer assiggneId, @PathVariable Integer orderId) {
        User copywriter = userService.find(assiggneId);
        OrderContainer container = orderService.findContainer(orderId);
        clientService.authorizeAssignee(container, copywriter);
    }

    // TODO opravneny client
    @PutMapping(value = "/change/id-assignee/{assiggneId}/id-container/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAssignee(@PathVariable Integer assiggneId, @PathVariable Integer orderId) {
        User copywriter = userService.find(assiggneId);
        OrderContainer container = orderService.findContainer(orderId);
        clientService.changeAssignee(container, copywriter);
    }

    // TODO opravneny client
    @PutMapping(value = "/visibility/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAssignee(@PathVariable Integer id) {
        OrderContainer container = orderService.findContainer(id);
        clientService.changeVisibility(container);
    }


}
