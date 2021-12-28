package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
import cz.cvut.kbss.ear.copyto.model.Contract;
import cz.cvut.kbss.ear.copyto.model.Conversation;
import cz.cvut.kbss.ear.copyto.model.Message;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.ContractService;
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
@RequestMapping("/rest/contracts")
public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
        // TODO probably
    }

    // --------------------CREATE--------------------------------------

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMessage(@RequestBody Contract contract) {
        contractService.createContract(contract);
        LOG.debug("Created contract {}.", contract);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", contract.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ----------------------------------------

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contract> getConracts() {
        return contractService.findContracts();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contract getById(@PathVariable Integer id) {
        final Contract contract = contractService.findContract(id);
        if (contract == null) {
            throw NotFoundException.create("contract", id);
        } return contract;
    }

    // TODO FIND by

    // --------------------UPDATE----------------------------------------

    // TODO DELETE , UPDATE BY ADMIN

    // --------------------DELETE----------------------------------------

}
