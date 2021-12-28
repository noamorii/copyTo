package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Workplace;
import cz.cvut.kbss.ear.copyto.rest.util.RestUtils;
import cz.cvut.kbss.ear.copyto.service.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/workplace")
public class WorkplaceController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(WorkplaceController.class);

    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService){
        this.workplaceService = workplaceService;
    }

    @PostFilter("hasRole('ROLE_ADMIN')") // TODO
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createWorkplace(@RequestBody Workplace workplace) {
        workplaceService.createWorkplace(workplace);
        LOG.debug("create workplace {}.", workplace);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", workplace.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // TODO filter
    @PutMapping(value = "/{id}, consumes = MediaType.APPLICATION_JSON_VALUE")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWorkplace(@PathVariable Integer id, @RequestBody Workplace workplace) {
        final Workplace original = workplaceService.findWorkplace(id);
        if(!original.getId().equals(workplace.getId())){
            throw new ValidationException("Workplace identifier in the data does not match the one in the request URL.");
        }
        workplaceService.update(workplace);
    }

    //TODO filter
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetWorkplace(@PathVariable Integer id){
        final Workplace toReset = workplaceService.findWorkplace(id);
        if(toReset == null){
            return;
        }
        workplaceService.reset(toReset);
        LOG.debug("Reset workplace {}.", toReset);
    }
}
