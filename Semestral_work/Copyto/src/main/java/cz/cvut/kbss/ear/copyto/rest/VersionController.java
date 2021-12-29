package cz.cvut.kbss.ear.copyto.rest;

import cz.cvut.kbss.ear.copyto.exception.ValidationException;
import cz.cvut.kbss.ear.copyto.model.Version;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/versions")
public class VersionController {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(WorkplaceController.class);

    private final WorkplaceService workplaceService;

    @Autowired
    public VersionController(WorkplaceService workplaceService){
        this.workplaceService = workplaceService;
    }

    //@PostFilter("hasRole('ADMIN')") // TODO
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Version> getVersions() {
        return workplaceService.findVersions();
    }

    //TODO
    @PostMapping(value = "workplace-id/{id}, consumes = MediaType.APPLICATION_JSON_VALUE")
    public ResponseEntity<Void> createVersion(@PathVariable Integer id, @RequestBody Version version) {
        Workplace workplace = workplaceService.findWorkplace(id);

        workplaceService.addVersion(version, workplace);
        LOG.debug("created version {}.", version);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", version.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // TODO filter
    @PutMapping(value = "/{id}, consumes = MediaType.APPLICATION_JSON_VALUE")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(@PathVariable Integer id, @RequestBody Version version) {
        final Version original = workplaceService.findVersion(id);
        if(!original.getId().equals(version.getId())){
            throw new ValidationException("Version identifier in the data does not match the one in the request URL.");
        }
        workplaceService.update(version);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVersion(@PathVariable Integer id, @RequestBody Workplace workplace){
        final Version toRemove = workplaceService.findVersion(id);
        if(toRemove == null) {
            return;
        }
        workplaceService.remove(workplace, toRemove);
        LOG.debug("Removed version {}.", toRemove);
    }
}
