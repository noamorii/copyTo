package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.service.ContractService;
import cz.cvut.kbss.ear.copyto.service.MessageService;
import cz.cvut.kbss.ear.copyto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
        // TODO probably
    }

    // --------------------CREATE--------------------------------------

    // --------------------READ--------------------------------------

    // --------------------UPDATE--------------------------------------

    // --------------------DELETE--------------------------------------
}
