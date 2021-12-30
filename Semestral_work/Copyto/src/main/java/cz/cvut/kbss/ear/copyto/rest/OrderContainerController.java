package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/containers")
public class OrderContainerController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(OrderContainerController.class);

    private final OrderService orderService;

    @Autowired
    public OrderContainerController(OrderService workplaceService){
        this.orderService = workplaceService;
    }

    // TODO filter
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrderContainer(@RequestBody OrderContainer container) {
        orderService.createContainer(container);
        LOG.debug("create container {}.", container);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/id/{id}", container.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ---------------------------------------------------------

    @GetMapping
    public List<OrderContainer> getAllContainers() {
        return orderService.findContainers();
    }

    @GetMapping(value="/id/{id}/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getCandidates(@PathVariable Integer id) {
        OrderContainer container = orderService.findContainer(id);
        return orderService.findCandidates(container);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getById(@PathVariable Integer id) {
        final Order order = orderService.findOrder(id);
        if (order == null) {
            throw NotFoundException.create("order", id);
        } return order;
    }

    // ---------------------------------------------------------

    // TODO filter
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody OrderContainer container) {
        final OrderContainer original = orderService.findContainer(id);
        if(!original.getId().equals(container.getId())){
            throw new ValidationException("OrderContainer identifier in the data does not match the one in the request URL.");
        }
        orderService.update(container);
    }

    // ---------------------------------------------------------

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeContainer(@PathVariable Integer id){
        final OrderContainer toRemove = orderService.findContainer(id);
        if(toRemove == null) {
            return;
        }
        orderService.remove(toRemove);
        LOG.debug("Removed version {}.", toRemove);
    }
}
