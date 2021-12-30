package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;
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
@RequestMapping("/rest/orders")
public class OrderController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(OrderContainerController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService workplaceService){
        this.orderService = workplaceService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COPYWRITER')")
    @GetMapping
    List<Order> getAllOrder() {
        return orderService.findOrders();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COPYWRITER')")
    @GetMapping(value="/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAvailableOrders() {
        return orderService.findAvailableOrders();
    }

    // todo vlastnik, admin
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderContainer getById(@PathVariable Integer id) {
        final OrderContainer container = orderService.findContainer(id);
        if (container == null) {
            throw NotFoundException.create("container", id);
        } return container;
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT')") // OPRAVNENY
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        LOG.debug("create order {}.", order);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/order/{id}", order.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // TODO OPRAVNENY COPYWRITER
    @PutMapping(value = "/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody Order order) {
        final Order original = orderService.findOrder(id);
        if(!original.getId().equals(order.getId())){
            throw new ValidationException("OrderDetail identifier in the data does not match the one in the request URL.");
        }
        orderService.update(order);
    }
}
