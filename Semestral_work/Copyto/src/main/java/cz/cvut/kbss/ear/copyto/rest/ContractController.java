package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Contract;
import cz.cvut.kbss.ear.copyto.model.Order;
import cz.cvut.kbss.ear.copyto.model.users.User;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.ContractService;
import cz.cvut.kbss.ear.copyto.service.OrderService;
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
@RequestMapping("/rest/contracts")
public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

    private final ContractService contractService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ContractController(ContractService contractService, UserService userService, OrderService orderService) {
        this.contractService = contractService;
        this.userService = userService;
        this.orderService = orderService;
    }

    // --------------------CREATE--------------------------------------

    @PostMapping(value = "client-id/{clientId}/copywriter-id/{copywriterId}/order-id/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createContract(@PathVariable Integer clientId, @PathVariable Integer copywriterId, @PathVariable Integer orderId, @RequestBody Contract contract) {
        contract.setClient(userService.find(clientId));
        contract.setCopywriter(userService.find(copywriterId));
        contract.setOrder(orderService.findOrder(orderId));
        contractService.createContract(contract);
        LOG.debug("Created contract {}.", contract);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/id/{id}", contract.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ----------------------------------------

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contract> getContracts() {
        return contractService.findContracts();
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contract getById(@PathVariable Integer id) {
        final Contract contract = contractService.findContract(id);
        if (contract == null) {
            throw NotFoundException.create("contract", id);
        } return contract;
    }

    @GetMapping(value = "/id-client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contract> getByClient(@PathVariable Integer id) {
        final User user = userService.find(id);
        final List<Contract> contracts = contractService.findContractsByClient(user);
        if (contracts == null) {
            throw NotFoundException.create("contract", id);
        } return contracts;
    }

    @GetMapping(value = "/id-copywriter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contract> getByCopywrite(@PathVariable Integer id) {
        final User user = userService.find(id);
        final List<Contract> contracts = contractService.findContractsByCopywriter(user);
        if (contracts == null) {
            throw NotFoundException.create("contract", id);
        } return contracts;
    }

    @GetMapping(value = "/id-order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contract> getByOrder(@PathVariable Integer id) {
        final Order order = orderService.findOrder(id);
        final List<Contract> contracts = contractService.findContract(order);
        if (contracts == null) {
            throw NotFoundException.create("contract", id);
        } return contracts;
    }
}
