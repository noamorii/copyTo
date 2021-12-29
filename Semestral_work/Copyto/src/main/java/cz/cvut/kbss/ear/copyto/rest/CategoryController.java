package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Category;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.OrderContainer;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.CategoryService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;
    private final OrderService orderService;

    @Autowired
    public CategoryController(CategoryService categoryService, OrderService orderService){
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    // --------------------CREATE--------------------------------------

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        LOG.debug("Created category {}.", category);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", category.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/id/{categoryId}/order/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOrderToCategory(@PathVariable Integer categoryId, @PathVariable Integer orderId) {
        final Category category = getById(categoryId);
        final Order order = orderService.findOrder(orderId);
        categoryService.addOrder(category, order);
        LOG.debug("Order {} added into category {}.", order, category);
    }

    // --------------------READ--------------------------------------


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories() {
        return categoryService.findCategories();
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getById(@PathVariable Integer id) {
        final Category category = categoryService.findCategory(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        } return category;
    }


    @GetMapping(value = "/id/{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrdersByCategory(@PathVariable Integer id) {
        return orderService.findOrders(getById(id));
    }


    // TODO tohle se mi nezda
/*    @GetMapping(value = "/{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderContainer> getOrdersContainersByCategory(@PathVariable Integer id) {
        List<OrderContainer> containers = new ArrayList<>();
        for (Order detail : orderService.findOrders(getById(id))) {
            containers.add(orderService.findContainer(detail));
        } return containers;
    }*/

    // --------------------DELETE--------------------------------------

    @DeleteMapping(value = "/id/{categoryId}/orders/id/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrderFromCategory(@PathVariable Integer categoryId,
                                        @PathVariable Integer orderId) {
        final Category category = getById(categoryId);
        final Order order = orderService.findOrder(orderId);
        if (order == null) {
            throw NotFoundException.create("Order", orderId);
        }
        categoryService.removeOrder(category, order);
        LOG.debug("Order {} removed from category {}.", order, category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCategory(@PathVariable Integer id) {
        final Category category = categoryService.findCategory(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        } categoryService.deleteCategory(category);
        LOG.debug("Category {} was deleted.", category);
    }

}
