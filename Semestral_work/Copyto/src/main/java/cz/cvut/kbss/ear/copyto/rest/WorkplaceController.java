package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.NotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("/rest/workplace")
public class WorkplaceController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(WorkplaceController.class);

    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService){
        this.workplaceService = workplaceService;
    }

    // --------------------CREATE--------------------------------------

    @PostFilter("hasRole('ROLE_ADMIN')") // TODO
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createWorkplace(@RequestBody Workplace workplace) {
        workplaceService.createWorkplace(workplace);
        LOG.debug("create workplace {}.", workplace);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", workplace.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // --------------------READ--------------------------------------

    @PostFilter("hasRole('ADMIN')") // TODO
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Workplace> getWorkplaces() {
        return workplaceService.findWorkplaces();
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workplace getById(@PathVariable Integer id) {
        final Workplace workplace = workplaceService.findWorkplace(id);
        if (workplace == null) {
            throw NotFoundException.create("workplace", id);
        } return workplace;
    }



    // --------------------UPDATE--------------------------------------

    // TODO filter
    @PutMapping(value = "/id-open/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeWorkplaceStatus(@PathVariable Integer id) {
        final Workplace original = workplaceService.findWorkplace(id);
        /*if(!original.getId().equals(workplace.getId())){
            throw new ValidationException("Workplace identifier in the data does not match the one in the request URL.");
        }*/
        workplaceService.changeWorkplaceStatus(original);
        workplaceService.update(original);
    }

    // --------------------DELETE--------------------------------------

    //TODO filter
    @DeleteMapping(value = "/id/{id}")
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
