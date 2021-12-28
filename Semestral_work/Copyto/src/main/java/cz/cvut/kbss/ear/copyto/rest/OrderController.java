package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.OrderService;
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
@RequestMapping("/rest/Details")
public class OrderController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(OrderContainerController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService workplaceService){
        this.orderService = workplaceService;
    }

    @GetMapping
    List<Order> getAllOrderDetails() {
        return orderService.findOrders();
    }

    // TODO filter
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrderDetail(@RequestBody Order order) {
        orderService.addOrder(order);
        LOG.debug("create order {}.", order);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", order.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // TODO filter
    @PutMapping(value = "/{id}, consumes = MediaType.APPLICATION_JSON_VALUE")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody Order detail) {
        final Order original = orderService.findOrder(id);
        if(!original.getId().equals(detail.getId())){
            throw new ValidationException("OrderDetail identifier in the data does not match the one in the request URL.");
        }
        orderService.update(detail);
    }
}
