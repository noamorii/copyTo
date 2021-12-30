package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.service.CopywriterService;
import cz.cvut.kbss.ear.copyto.service.OrderService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/copywriter")
public class CopywriterController {

    private static final Logger LOG = LoggerFactory.getLogger(CopywriterController.class);

    private final CopywriterService copywriterService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public CopywriterController(CopywriterService copywriterService,  UserService userService, OrderService orderService){
        this.copywriterService = copywriterService;
        this.userService = userService;
        this.orderService = orderService;
    }

    // TODO opravneny copywriter
    @PutMapping(value = "/id-container/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authorizeAssignee(@PathVariable Integer id) {
        OrderContainer container = orderService.findContainer(id);
        copywriterService.finishJob(container);
    }

    // TODO opravneny copywriter
    @PutMapping(value = "/user/{userId}/order/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signUpForOrder(@PathVariable Integer orderId, @PathVariable Integer userId) {
        OrderContainer container = orderService.findContainer(orderId);
        User user = userService.find(userId);
        copywriterService.signUpForOrder(user, container.getOrder());
    }
}